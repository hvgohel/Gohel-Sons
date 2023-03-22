package com.gohel.controller;

import com.gohel.model.Student;
import com.gohel.model.User;
import com.gohel.service.StudentService;
import com.gohel.service.UserService;
import com.gohel.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

import static com.gohel.utils.API.*;
import static com.gohel.utils.API.SEARCH;
import static com.gohel.utils.API.USER;
import static com.gohel.utils.Constants.*;

@Controller
@RequiredArgsConstructor
public class DashboardController {

  private final StudentService studentService;
  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;

  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("user", userService.currentUser());
    return "login";
  }

  @RequestMapping({ROOT, SEARCH})
  public String index(@RequestParam(defaultValue = "1", required = false) Integer pageNumber,
      Model model, String search) {
    if (!StringUtils.isEmpty(ENABLE_MODULE)) {
      return STOCKS_REDIRECT_INDEX;
    }
    Page<Student> page = new PageImpl(Collections.EMPTY_LIST);
    if (StringUtils.isEmpty(search)) {
      page = studentService.getAllByUser(pageNumber);
    } else {
      page = studentService.search(pageNumber, search);
    }

    Utils.setPagination(page, model, search, 0.0, userService.currentUser());
    return DASHBOARD_REDIRECT_INDEX;
  }

  @RequestMapping(PAYMENT)
  public String edit(@PathVariable Long id, String payment, Integer pageNumber, String search) {
    Student customer = studentService.get(id);
    customer.setPayment(payment.equalsIgnoreCase("Unpaid") ? "Paid" : "Unpaid");
    studentService.save(customer);
    String redirectUrl = "redirect:/?pageNumber=" + pageNumber;
    if (!StringUtils.isEmpty(search) && !search.equalsIgnoreCase("null")) {
      redirectUrl = "redirect:/search?pageNumber=" + pageNumber + "&search=" + search;
    }

    return redirectUrl;
  }

  @GetMapping(PROFILE)
  public String get(Model model) {
    model.addAttribute("user", userService.currentUser());
    return USER_REDIRECT_PROFILE;
  }

  @PostMapping(USER + PROFILE)
  public String update(User user,
      @RequestParam(value = "profilePic", required = false) MultipartFile file) throws IOException {
    String password = user.getPassword();
    if (file != null && !file.isEmpty()) {
      user.setProfile(Base64Utils.encodeToString(file.getBytes()));
    }
    user.setPassword(passwordEncoder.encode(password));
    userService.save(user);
    return USER_REDIRECT_PROFILE;
  }
}
