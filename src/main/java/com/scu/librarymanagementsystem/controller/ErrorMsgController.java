package com.scu.librarymanagementsystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/index")
@Api(tags = "错误信息路由")
public class ErrorMsgController {
    @GetMapping("/unLogin")
    @ApiOperation("未登录")
    public String unLogin() {
        return "未登录，请登录后再操作！";
    }

    @GetMapping("/unAuth")
    @ApiOperation("未授权")
    public String unAuth() {
        return "当前用户无操作权限！";
    }
}
