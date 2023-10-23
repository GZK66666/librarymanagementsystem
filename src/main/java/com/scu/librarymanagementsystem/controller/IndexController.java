package com.scu.librarymanagementsystem.controller;

import io.swagger.annotations.Api;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
@Api(tags = "视图")
public class IndexController {
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/user-table")
    public String userTablePage() {
        return "user-table";
    }

    @GetMapping("/user-add")
    public String userAddPage() {
        return "user-add";
    }

    @GetMapping("/user-edit")
    public String userEditPage() {
        return "user-edit";
    }

    @GetMapping("/book-table")
    public String bookTablePage() {
        return "book-table";
    }

    @GetMapping("/book-add")
    public String bookAddPage() {
        return "book-add";
    }

    @GetMapping("/book-edit")
    public String bookEditPage() {
        return "book-edit";
    }
}
