package com.scu.librarymanagementsystem.controller;

import com.scu.librarymanagementsystem.common.enums.UserType;
import com.scu.librarymanagementsystem.model.User;
import com.scu.librarymanagementsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
        List<User> foundUsers = userService.findUsersByUserName(userName);

        if (foundUsers == null || foundUsers.isEmpty()) {
            return "username error!";
        }

        for (User user : foundUsers) {
            if (user.getPassword().equals(passWord)) {
                return "login success";
            }
        }

        return "password error!";
    }

    //    curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "userName=jbl&passWord=123456" http://localhost:8080/api/Users/add
    @PostMapping("/add")
    @ApiOperation("新增用户")
    public ResponseEntity<String> addUser(@RequestParam String userName, @RequestParam String passWord) {
        Long id = userService.addUser(userName, passWord, UserType.USER); // todo: hardcode user type

        if (id > 0) {
            return new ResponseEntity<>("User with ID " + id + " created.", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("add user failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/delete")
    @ApiOperation("删除用户")
    public void deleteUser(@RequestParam String userName) {
        userService.deleteUser(userName);
    }

    @GetMapping("/updateUserName")
    @ApiOperation("更新用户名")
    public int updateUserName(@RequestParam String oldUserName, @RequestParam String newUserName) {
        return userService.updateUserName(oldUserName, newUserName);
    }

    @GetMapping("/updatePassWord")
    @ApiOperation("更新密码")
    public int updatePassWord(@RequestParam String userName, @RequestParam String newPassword) {
        return userService.updatePassword(userName, newPassword);
    }

    @GetMapping("/updateUserType")
    @ApiOperation("更新用户类型")
    public int updateUserType(@RequestParam String userName, @RequestParam String userType) {
        return userService.updateUserType(userName, userType.equals("admin")?UserType.ADMIN:UserType.USER);
    }

    @GetMapping("/findUsersByUsernameAndUserType")
    @ApiOperation("搜索用户")
    @CrossOrigin // todo: remove when dev
    public List<User> findUsersByUserNameAndUserType(@RequestParam String userName, @RequestParam String userType) {
        if (userName.isEmpty() && userType.isEmpty()) {
            return new ArrayList<>();
        }

        if (userName.isEmpty()) {
            return userService.findUsersByUserType(userType.equals("admin")? UserType.ADMIN: UserType.USER);
        }

        if (userType.isEmpty()) {
            return userService.findUsersByUserName(userName);
        }

        return userService.findUsersByUserNameAndUserType(userName, userType.equals("admin")? UserType.ADMIN: UserType.USER);
    }
}
