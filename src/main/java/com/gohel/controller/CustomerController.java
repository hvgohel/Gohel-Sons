package com.gohel.controller;

import com.gohel.model.Student;
import com.gohel.service.StudentService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.gohel.utils.API.SEARCH;
import static com.gohel.utils.API.*;
import static com.gohel.utils.Constants.*;
import static com.gohel.utils.Constants.USER;

@Controller
@RequiredArgsConstructor
@RequestMapping(CUSTOMERS)
public class CustomerController {

  private final StudentService studentService;
  private final UserService userService;

  @RequestMapping
  public String index() {
    return CUSTOMERS_REDIRECT_INDEX;
  }

  @GetMapping({INDEX, SEARCH})
  public String list(@RequestParam(defaultValue = "1", required = false) Integer pageNumber,
      Model model, String search) {
    Page<Student> page = new PageImpl(Collections.EMPTY_LIST);
    if (StringUtils.isEmpty(search)) {
      page = studentService.getCustomers(pageNumber);
    } else {
      page = studentService.searchCustomer(pageNumber, search);
    }

    Utils.setPagination(page, model, search,
        page.stream().mapToDouble(t -> Double.valueOf(t.getPrice())).sum(),
        userService.currentUser());
    return CUSTOMERS_REDIRECT_LIST;
  }

  @RequestMapping(ADD)
  public String add(Model model) {
    model.addAttribute(USER, userService.currentUser());
    model.addAttribute("student", new Student());
    return CUSTOMERS_REDIRECT_FORM;
  }

  @RequestMapping(EDIT)
  public String edit(@PathVariable Long id, Model model) {
    model.addAttribute(USER, userService.currentUser());
    model.addAttribute("student", studentService.get(id));
    return CUSTOMERS_REDIRECT_FORM;
  }

  @RequestMapping(PAYMENT)
  public String edit(@PathVariable Long id, String payment, Integer pageNumber, String search) {
    Student customer = studentService.get(id);
    customer.setPayment(payment.equalsIgnoreCase("Unpaid") ? "Paid" : "Unpaid");
    studentService.save(customer);

    String redirectUrl = "redirect:/customers/index?pageNumber=" + pageNumber;
    if (!StringUtils.isEmpty(search) && !search.equalsIgnoreCase("null")) {
      redirectUrl = "redirect:/customers/search?pageNumber=" + pageNumber + "&search=" + search;
    }

    return redirectUrl;
  }

  @PostMapping(SAVE)
  public String save(Student customer, final RedirectAttributes ra) {
    customer.setUser(userService.currentUser());
    studentService.save(customer);
    ra.addFlashAttribute("successFlash", "customer save successfully");
    return CUSTOMERS_REDIRECT;
  }

  @RequestMapping(DELETE)
  public String delete(@PathVariable Long id) {
    studentService.delete(id);
    return CUSTOMERS_REDIRECT;
  }

  @RequestMapping(INVOICE)
  public void invoice(@PathVariable Long id, HttpServletResponse response) throws IOException {
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=customer-invoice.pdf");
    studentService.invoice(response, id);
  }
}
