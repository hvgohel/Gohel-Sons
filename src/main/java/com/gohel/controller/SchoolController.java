package com.gohel.controller;

import com.gohel.model.School;
import com.gohel.service.SchoolService;
import com.gohel.service.StudentService;
import com.gohel.service.UserService;
import com.gohel.utils.API;
import com.gohel.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.gohel.utils.API.SEARCH;
import static com.gohel.utils.API.*;
import static com.gohel.utils.Constants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(API.SCHOOLS)
public class SchoolController {

  private final UserService userService;
  private final SchoolService schoolService;
  private final StudentService studentService;

  @RequestMapping
  public String index() {
    return SCHOOLS_REDIRECT_INDEX;
  }

  @GetMapping({INDEX, SEARCH})
  public String list(@RequestParam(defaultValue = "1", required = false) Integer pageNumber,
      Model model, String search) {
    Page<School> page = new PageImpl(Collections.EMPTY_LIST);
    if (StringUtils.isEmpty(search)) {
      page = schoolService.getSchools(pageNumber);
    } else {
      page = schoolService.getSchoolsBySearch(pageNumber, search);
    }

    for (School school : page.getContent()) {
      Integer total = studentService.getTotalAmount(school.getId());
      school.setTotalAmount(total == null ? 0 : total);
      school.setTotalStudent(studentService.getTotalStudent(school.getId()));
    }

    Utils.setPagination(page, model, search,
        page.stream().mapToDouble(t -> Double.valueOf(t.getTotalAmount())).sum());
    return SCHOOLS_REDIRECT_LIST;
  }

  @RequestMapping(ADD)
  public String add(Model model) {
    model.addAttribute("school", new School());
    return SCHOOLS_REDIRECT_FORM;
  }

  @RequestMapping(EDIT)
  public String edit(@PathVariable Long id, Model model) {
    model.addAttribute("school", schoolService.get(id));
    return SCHOOLS_REDIRECT_FORM;
  }

  @PostMapping(SAVE)
  public String save(School school, final RedirectAttributes ra) {
    school.setUser(userService.currentUser());
    schoolService.save(school);
    ra.addFlashAttribute("successFlash", "School save successfully");
    return SCHOOLS_REDIRECT;
  }

  @RequestMapping(DELETE)
  public String delete(@PathVariable Long id) {
    schoolService.delete(id);
    return SCHOOLS_REDIRECT;
  }

  @RequestMapping(INVOICE)
  public void invoice(@PathVariable Long id, HttpServletResponse response) throws IOException {
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=school-invoice.pdf");
    schoolService.invoice(response, id);
  }
}
