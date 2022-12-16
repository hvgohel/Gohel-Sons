package com.gohel.controller;

import com.gohel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gohel.model.School;
import com.gohel.service.SchoolService;
import com.gohel.service.StudentService;

@Controller
@RequiredArgsConstructor
public class SchoolController {

  private final UserService userService;
  private final SchoolService schoolService;
  private final StudentService studentService;

  @RequestMapping(value = "/schools")
  public String index() {
    return "redirect:/schools/1";
  }

  @RequestMapping(value = "/schools/{pageNumber}", method = RequestMethod.GET)
  public String list(@PathVariable Integer pageNumber, Model model) {
    Page<School> page = schoolService.getSchools(pageNumber);

    for (School school : page.getContent()) {
      school.setTotalAmount(studentService.getTotalAmount(school.getId()));
      school.setTotalStudent(studentService.getTotalStudent(school.getId()));
    }

    int current = page.getNumber() + 1;
    int begin = Math.max(1, current - 5);
    int end = Math.min(begin + 10, page.getTotalPages());

    model.addAttribute("list", page);
    model.addAttribute("beginIndex", begin);
    model.addAttribute("endIndex", end);
    model.addAttribute("currentIndex", current);

    return "schools/list";
  }

  @RequestMapping("/schools/add")
  public String add(Model model) {
    model.addAttribute("school", new School());
    return "schools/form";
  }

  @RequestMapping("/schools/edit/{id}")
  public String edit(@PathVariable Long id, Model model) {
    model.addAttribute("school", schoolService.get(id));
    return "schools/form";
  }

  @RequestMapping(value = "/schools/save", method = RequestMethod.POST)
  public String save(School school, final RedirectAttributes ra) {
    school.setUser(userService.currentUser());
    schoolService.save(school);
    ra.addFlashAttribute("successFlash", "School save successfully");
    return "redirect:/schools";
  }

  @RequestMapping("/schools/delete/{id}")
  public String delete(@PathVariable Long id) {
    schoolService.delete(id);
    return "redirect:/schools";
  }
}
