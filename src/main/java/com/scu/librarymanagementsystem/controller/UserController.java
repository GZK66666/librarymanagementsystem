package com.scu.librarymanagementsystem.controller;

import com.scu.librarymanagementsystem.common.enums.UserType;
import com.scu.librarymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestParam String userName, @RequestParam String passWord) {
        Long id = userService.addUser(userName, passWord, UserType.USER); // todo: hardcode user type

        if (id > 0) {
            return new ResponseEntity<>("User with ID " + id + " created.", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("add user failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
