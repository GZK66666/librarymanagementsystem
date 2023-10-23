package com.scu.librarymanagementsystem.controller;

import com.scu.librarymanagementsystem.model.User;
import com.scu.librarymanagementsystem.service.UserService;
import com.scu.librarymanagementsystem.utils.UserType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Users")
@Api(tags = "用户管理")
@CrossOrigin(origins = "http://localhost:63342") // 允许跨域请求
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    @ApiOperation("登陆")
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
        return userService.addUser(userName, passWord, userType.equalsIgnoreCase("admin")? UserType.ADMIN :UserType.USER);
    }

    @GetMapping("/delete")
    @ApiOperation("删除用户")
    public int deleteUser(@RequestParam Long id) {
        return userService.deleteUserById(id);
    }

    @GetMapping("/updateUser")
    @ApiOperation("更新用户")
    public int updateUser(@RequestParam Long id, @RequestParam String newUsername, @RequestParam String newPassword, @RequestParam String newUserType) {
        return userService.updateUser(id, newUsername, newPassword, newUserType.equalsIgnoreCase("admin")?UserType.ADMIN:UserType.USER);
    }

    @GetMapping("/findUsers")
    @ApiOperation("搜索用户")
    public Map<String, Object> findUsersByUserNameAndUserType(@RequestParam(required = false) String userName, @RequestParam(required = false) String userType) {
        List<User> users =  userService.findUsersByMultiConditions(userName, userType);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "");
        response.put("count", users.size());
        response.put("data", users);

        return response;
    }

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
