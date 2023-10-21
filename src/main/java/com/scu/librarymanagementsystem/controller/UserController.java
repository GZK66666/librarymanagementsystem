package com.scu.librarymanagementsystem.controller;

import com.scu.librarymanagementsystem.common.enums.UserType;
import com.scu.librarymanagementsystem.model.User;
import com.scu.librarymanagementsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/Users")
@Api(tags = "用户管理")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    @ApiOperation("登陆")
    @CrossOrigin // todo: remove when dev
    public String login(@RequestParam String userName, @RequestParam String passWord) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);

        try {
            subject.login(token);
            return "login success";
        } catch (UnknownAccountException e) {
            return "username error!";
        } catch (IncorrectCredentialsException e) {
            return "password error!";
        }
    }

    @PostMapping("/add")
    @ApiOperation("新增用户")
    public int addUser(@RequestParam String userName, @RequestParam String passWord, @RequestParam String userType) {
        return userService.addUser(userName, passWord, userType.equals("admin")?UserType.ADMIN:UserType.USER);
    }

    @GetMapping("/delete")
    @ApiOperation("删除用户")
    public int deleteUser(@RequestParam Long id) {
        return userService.deleteUserById(id);
    }

    @GetMapping("/updateUser")
    @ApiOperation("更新用户")
    public int updateUser(@RequestParam Long id, @RequestParam String newUsername, @RequestParam String newPassword, @RequestParam String newUserType) {
        return userService.updateUser(id, newUsername, newPassword, newUserType.equals("admin")?UserType.ADMIN:UserType.USER);
    }

    @GetMapping("/findUsers")
    @ApiOperation("搜索用户")
    @CrossOrigin // todo: remove when dev
    public List<User> findUsersByUserNameAndUserType(@RequestParam(required = false) String userName, @RequestParam(required = false) String userType) {
        return userService.findUsersByMultiConditions(userName, userType);
    }
}
