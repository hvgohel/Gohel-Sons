package com.gohel.controller;

import com.gohel.model.Item;
import com.gohel.service.ItemService;
import com.gohel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Collections;

@Controller
@RequestMapping(value = "/stocks")
@RequiredArgsConstructor
public class StockController {

  final private ItemService itemService;
  final private UserService userService;

  @RequestMapping
  public String index() {
    return "redirect:/stocks/index";
  }

  @RequestMapping(value = {"/index", "/search"}, method = RequestMethod.GET)
  public String list(@RequestParam(defaultValue = "1", required = false) Integer pageNumber,
      Model model, String search) {
    Page<Item> page = new PageImpl(Collections.EMPTY_LIST);
    if (StringUtils.isEmpty(search)) {
      page = itemService.getItems(pageNumber);
    } else {
      page = itemService.getIteamsBySearch(pageNumber, search);
    }

    int current = page.getNumber() + 1;
    int begin = Math.max(1, current - 5);
    int end = Math.min(begin + 10, page.getTotalPages());

    model.addAttribute("list", page);
    model.addAttribute("beginIndex", begin);
    model.addAttribute("endIndex", end);
    model.addAttribute("currentIndex", current);
    model.addAttribute("search", search);
    return "stocks/list";
  }

  @RequestMapping("/add")
  public String add(Model model) {
    model.addAttribute("item", new Item());
    return "stocks/form";
  }

  @PostMapping(value = "/save")
  public String save(Item item, final RedirectAttributes ra) {
    item.setUser(userService.currentUser());
    itemService.save(item);
    ra.addFlashAttribute("successFlash", "item save successfully");
    return "redirect:/stocks/index";
  }

  @PostMapping("/billUpload/{id}")
  public String upload(@PathVariable Long id, @RequestParam("bill") MultipartFile file) throws IOException {
    itemService.upload(id, file.getBytes());
    return "redirect:/stocks/index";
  }
}
