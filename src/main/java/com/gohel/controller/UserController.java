package com.gohel.controller;

import com.gohel.model.User;
import com.gohel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityExistsException;

import static com.gohel.utils.API.REGISTER;
import static com.gohel.utils.Constants.USER_REDIRECT_LOGIN;
import static com.gohel.utils.Constants.USER_REDIRECT_REGISTRATION;

@Controller
@RequiredArgsConstructor
@RequestMapping(REGISTER)
public class UserController {

  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;

  @GetMapping
  public String register(Model model) {
    model.addAttribute("user", new User());
    return USER_REDIRECT_REGISTRATION;
  }

  @PostMapping
  public String register(User user) {
    if (userService.existsByUsername(user.getUsername())) {
      throw new EntityExistsException("Username already exist");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userService.save(user);
    return USER_REDIRECT_LOGIN;
  }
}
