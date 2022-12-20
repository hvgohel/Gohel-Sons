package com.gohel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.gohel.model.Student;
import com.gohel.service.StudentService;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final StudentService studentService;

    @RequestMapping(path = {"/", "/search"})
    public String index(@RequestParam(defaultValue = "1", required = false) Integer pageNumber,
                        Model model, String search) {
        Page<Student> page = new PageImpl(Collections.EMPTY_LIST);
        if (StringUtils.isEmpty(search)) {
            page = studentService.getAllByUser(pageNumber);
        } else {
            page = studentService.getAllByUserAndName(pageNumber, search);
        }

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("search", search);
        return "dashboard/index";
    }

    @RequestMapping("/payment/{id}")
    public String edit(@PathVariable Long id) {
        Student customer = studentService.get(id);
        customer.setPayment("Paid");
        studentService.save(customer);
        return "redirect:/";
    }
}
