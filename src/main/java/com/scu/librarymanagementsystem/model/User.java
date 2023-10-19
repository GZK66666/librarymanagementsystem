package com.scu.librarymanagementsystem.model;

import com.scu.librarymanagementsystem.common.enums.UserType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id // 主键的意思
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键使用自增
    private Long id;
    private String username;
    private String password;
    private UserType userType;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}

