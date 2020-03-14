package com.gohel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.gohel.model.Student;
import com.gohel.service.StudentService;

@Controller
public class DashboardController {

  @Autowired
  private StudentService studentService;

  @RequestMapping
  public String index(@RequestParam(defaultValue = "1", required = false) Integer pageNumber,
      Model model) {
    Page<Student> page = studentService.getAll(PageRequest.of(pageNumber - 1, 20));

    int current = page.getNumber() + 1;
    int begin = Math.max(1, current - 5);
    int end = Math.min(begin + 10, page.getTotalPages());

    model.addAttribute("list", page);
    model.addAttribute("beginIndex", begin);
    model.addAttribute("endIndex", end);
    model.addAttribute("currentIndex", current);
    return "dashboard/index";
  }
}
