package com.gohel.controller;

import com.gohel.model.Item;
import com.gohel.service.ItemService;
import com.gohel.service.UserService;
import com.gohel.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

import static com.gohel.utils.API.SEARCH;
import static com.gohel.utils.API.*;
import static com.gohel.utils.Constants.USER;
import static com.gohel.utils.Constants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(STOCKS)
public class StockController {

  final private ItemService itemService;
  final private UserService userService;

  @RequestMapping
  public String index() {
    return STOCKS_REDIRECT_INDEX;
  }

  @GetMapping({INDEX, SEARCH})
  public String list(@RequestParam(defaultValue = "1", required = false) Integer pageNumber,
      Model model, String search) {
    Page<Item> page = new PageImpl(Collections.EMPTY_LIST);
    if (StringUtils.isEmpty(search)) {
      page = itemService.getItems(pageNumber);
    } else {
      page = itemService.search(pageNumber, search);
    }

    Utils.setPagination(page, model, search,
        page.stream().mapToDouble(t -> Double.valueOf(t.getAmount())).sum(),
        userService.currentUser());
    return STOCKS_REDIRECT_LIST;
  }

  @RequestMapping(ADD)
  public String add(Model model) {
    model.addAttribute(USER, userService.currentUser());
    model.addAttribute("item", new Item());
    return STOCK_REDIRECT_FORM;
  }

  @PostMapping(SAVE)
  public String save(Item item,
      @RequestParam(value = "designImage", required = false) MultipartFile designImage,
      @RequestParam(value = "receiptImg", required = false) MultipartFile receipt)
      throws IOException {
    item.setUser(userService.currentUser());
    if (designImage != null && !designImage.isEmpty()) {
      item.setDesignImg(Base64Utils.encodeToString(designImage.getBytes()));
    }

    if (receipt != null && !receipt.isEmpty()) {
      item.setReceipt(Base64Utils.encodeToString(receipt.getBytes()));
    }

    itemService.save(item);
    return STOCKS_REDIRECT_INDEX;
  }

  @PostMapping(BILL_UPLOAD)
  public String upload(@PathVariable Long id, @RequestParam("bill") MultipartFile file)
      throws IOException {
    itemService.upload(id, file.getBytes());
    return STOCKS_REDIRECT_INDEX;
  }

  @RequestMapping(EDIT)
  public String edit(@PathVariable Long id, Model model) {
    model.addAttribute(USER, userService.currentUser());
    model.addAttribute("item", itemService.get(id));
    return STOCK_REDIRECT_FORM;
  }

  @RequestMapping(DELETE)
  public String delete(@PathVariable Long id) {
    itemService.delete(id);
    return STOCKS_REDIRECT;
  }
}

