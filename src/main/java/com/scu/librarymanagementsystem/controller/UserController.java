package com.scu.librarymanagementsystem.controller;

import com.scu.librarymanagementsystem.common.enums.UserType;
import com.scu.librarymanagementsystem.model.User;
import com.scu.librarymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Users")
public class UserController {
    @Autowired
    UserService userService;

//    curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "userName=jbl&passWord=123456" http://localhost:8080/api/Users/add
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestParam String userName, @RequestParam String passWord) {
        Long id = userService.addUser(userName, passWord, UserType.USER); // todo: hardcode user type

        if (id > 0) {
            return new ResponseEntity<>("User with ID " + id + " created.", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("add user failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String passWord) {
        User findedUser = userService.findUserByUserName(userName);

        if (findedUser == null) {
            return "username error!";
        }

        if (!passWord.equals(findedUser.getPassword())) {
            return "password error!";
        }

        return "login success";
    }
}
