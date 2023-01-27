package com.gohel.controller;

import com.gohel.model.Transaction;
import com.gohel.service.TransactionService;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gohel.utils.API.SEARCH;
import static com.gohel.utils.API.*;
import static com.gohel.utils.Constants.*;
import static com.gohel.utils.Constants.USER;

@Controller
@RequiredArgsConstructor
@RequestMapping(TRANSACTIONS)
public class TransactionController {

  final private TransactionService transactionService;
  final private UserService userService;

  @RequestMapping
  public String index() {
    return TRANSACTIONS_REDIRECT_INDEX;
  }

  @GetMapping({INDEX, SEARCH})
  public String list(@RequestParam(defaultValue = "1", required = false) Integer pageNumber,
      Model model, String search) {
    Page<Transaction> page = new PageImpl(Collections.EMPTY_LIST);
    if (StringUtils.isEmpty(search)) {
      page = transactionService.getTransactions(pageNumber);
    } else {
      page = transactionService.search(pageNumber, search);
    }

    Utils.setPagination(page, model, search,
        page.stream().mapToDouble(t -> Double.valueOf(t.getAmount())).sum(),
        userService.currentUser());
    return TRANSACTIONS_REDIRECT_LIST;
  }

  @RequestMapping(ADD)
  public String add(Model model) {
    model.addAttribute("transaction", new Transaction());
    model.addAttribute(USER, userService.currentUser());
    return TRANSACTIONS_REDIRECT_FORM;
  }

  @PostMapping(SAVE)
  public String save(Transaction transaction, final RedirectAttributes ra) {
    transaction.setUser(userService.currentUser());
    transactionService.save(transaction);
    ra.addFlashAttribute("successFlash", "save successfully");
    return TRANSACTIONS_REDIRECT_INDEX;
  }

  @RequestMapping(EDIT)
  public String edit(@PathVariable Long id, Model model) {
    model.addAttribute("transaction", transactionService.get(id));
    model.addAttribute(USER, userService.currentUser());
    return TRANSACTIONS_REDIRECT_FORM;
  }

  @RequestMapping(DELETE)
  public String delete(@PathVariable Long id) {
    transactionService.delete(id);
    return TRANSACTIONS_REDIRECT;
  }
}

