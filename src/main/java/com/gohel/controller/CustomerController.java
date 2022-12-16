package com.gohel.controller;

import com.gohel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gohel.model.Student;
import com.gohel.service.StudentService;

@Controller
@RequestMapping(value = "/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final StudentService studentService;
  private final UserService userService;

  @RequestMapping
  public String index() {
    return "redirect:/customers/1";
  }

  @RequestMapping(value = "/{pageNumber}", method = RequestMethod.GET)
  public String list(@PathVariable Integer pageNumber, Model model) {
    Page<Student> page = studentService.getCustomers(pageNumber);

    int current = page.getNumber() + 1;
    int begin = Math.max(1, current - 5);
    int end = Math.min(begin + 10, page.getTotalPages());

    model.addAttribute("list", page);
    model.addAttribute("beginIndex", begin);
    model.addAttribute("endIndex", end);
    model.addAttribute("currentIndex", current);
    return "customers/list";
  }

  @RequestMapping("/add")
  public String add(Model model) {
    model.addAttribute("student", new Student());
    return "customers/form";
  }

  @RequestMapping("/edit/{id}")
  public String edit(@PathVariable Long id, Model model) {
    model.addAttribute("student", studentService.get(id));
    return "customers/form";
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(Student customer, final RedirectAttributes ra) {
    customer.setUser(userService.currentUser());
    studentService.save(customer);
    ra.addFlashAttribute("successFlash", "customer save successfully");
    return "redirect:/customers";
  }

  @RequestMapping("/delete/{id}")
  public String delete(@PathVariable Long id) {
    studentService.delete(id);
    return "redirect:/customers";
  }
}