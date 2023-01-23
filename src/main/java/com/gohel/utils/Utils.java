package com.gohel.utils;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import static com.gohel.utils.Constants.*;

public class Utils {
  public static void setPagination(Page<?> page, Model model, String search, Double total) {
    int current = page.getNumber() + 1;
    int begin = Math.max(1, current - 5);
    int end = Math.min(begin + 10, page.getTotalPages());

    model.addAttribute(LIST, page);
    model.addAttribute(BEGIN_INDEX, begin);
    model.addAttribute(END_INDEX, end);
    model.addAttribute(CURRENT_INDEX, current);
    model.addAttribute(SEARCH, search);
    model.addAttribute(TOTAL, total);
  }
}
