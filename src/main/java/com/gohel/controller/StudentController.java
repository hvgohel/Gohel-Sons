package com.gohel.controller;

import com.gohel.service.UserService;
import com.gohel.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gohel.model.Student;
import com.gohel.service.SchoolService;
import com.gohel.service.StudentService;

import java.util.Collections;

import static com.gohel.utils.API.*;
import static com.gohel.utils.Constants.*;
import static com.gohel.utils.Constants.SEARCH;

@Controller
@RequiredArgsConstructor
@RequestMapping(STUDENTS)
public class StudentController {

  private final StudentService studentService;
  private final SchoolService schoolService;
  private final UserService userService;

  @RequestMapping
  public String index() {
    return STUDENTS_REDIRECT_INDEX;
  }

  @GetMapping({INDEX, SEARCH})
  public String list(@RequestParam(defaultValue = "1", required = false) Integer pageNumber, Model model, String search) {
    Page<Student> page = new PageImpl(Collections.EMPTY_LIST);
    if (StringUtils.isEmpty(search)) {
      page = studentService.getStudents(pageNumber);
    } else {
      page = studentService.searchStudent(pageNumber, search);
    }

    Utils.setPagination(page, model, search, page.stream().mapToDouble(t -> Double.valueOf(t.getPrice())).sum());
    return STUDENTS_REDIRECT_LIST;
  }

  @RequestMapping(ADD)
  public String add(Model model) {
    model.addAttribute("student", new Student());
    model.addAttribute("schools", schoolService.getAllSchool());
    return STUDENTS_REDIRECT_FORM;
  }

  @RequestMapping(EDIT)
  public String edit(@PathVariable Long id, Model model) {
    model.addAttribute("student", studentService.get(id));
    model.addAttribute("schools", schoolService.getAllSchool());
    return STUDENTS_REDIRECT_FORM;
  }

  @PostMapping(SAVE)
  public String save(Student student, final RedirectAttributes ra) {
    student.setUser(userService.currentUser());
    studentService.save(student);
    ra.addFlashAttribute("successFlash", "student save successfully");
    return STUDENTS_REDIRECT;
  }

  @RequestMapping(DELETE)
  public String delete(@PathVariable Long id) {
    studentService.delete(id);
    return STUDENTS_REDIRECT;
  }

  @RequestMapping(PAYMENT)
  public String edit(@PathVariable Long id, String payment, Integer pageNumber, String search) {
    Student customer = studentService.get(id);
    customer.setPayment(payment.equalsIgnoreCase("Unpaid") ? "Paid" : "Unpaid");
    studentService.save(customer);

    String redirectUrl = "redirect:/students/index?pageNumber=" + pageNumber;
    if (!StringUtils.isEmpty(search) && !search.equalsIgnoreCase("null")) {
      redirectUrl = "redirect:/students/search?pageNumber=" + pageNumber + "&search=" + search;
    }
    return redirectUrl;
  }
}

