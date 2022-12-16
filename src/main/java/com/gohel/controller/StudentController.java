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
import com.gohel.model.Student;
import com.gohel.service.SchoolService;
import com.gohel.service.StudentService;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;
  private final SchoolService schoolService;
  private final UserService userService;

  @RequestMapping
  public String index() {
    return "redirect:/students/1";
  }

  @RequestMapping(value = "/{pageNumber}", method = RequestMethod.GET)
  public String list(@PathVariable Integer pageNumber, Model model) {
    Page<Student> page = studentService.getStudents(pageNumber);

    int current = page.getNumber() + 1;
    int begin = Math.max(1, current - 5);
    int end = Math.min(begin + 10, page.getTotalPages());

    model.addAttribute("list", page);
    model.addAttribute("beginIndex", begin);
    model.addAttribute("endIndex", end);
    model.addAttribute("currentIndex", current);
    return "students/list";
  }

  @RequestMapping("/add")
  public String add(Model model) {
    model.addAttribute("student", new Student());
    model.addAttribute("schools", schoolService.getAllSchool());
    return "students/form";
  }

  @RequestMapping("/edit/{id}")
  public String edit(@PathVariable Long id, Model model) {
    model.addAttribute("student", studentService.get(id));
    model.addAttribute("schools", schoolService.getAllSchool());
    return "students/form";
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(Student student, final RedirectAttributes ra) {
    student.setUser(userService.currentUser());
    studentService.save(student);
    ra.addFlashAttribute("successFlash", "student save successfully");
    return "redirect:/students";
  }

  @RequestMapping("/delete/{id}")
  public String delete(@PathVariable Long id) {
    studentService.delete(id);
    return "redirect:/students";
  }
}
