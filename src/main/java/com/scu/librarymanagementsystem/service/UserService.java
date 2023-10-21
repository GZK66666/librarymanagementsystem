package com.scu.librarymanagementsystem.service;

import com.scu.librarymanagementsystem.common.enums.UserType;
import com.scu.librarymanagementsystem.model.User;
import com.scu.librarymanagementsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public int addUser(String userName, String passWord, UserType userType) {
        try {
            User user = new User();
            user.setUsername(userName);
            user.setPassword(passWord);
            user.setUserType(userType);

            userRepository.save(user);

            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    @Transactional
    public int deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);

            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }


    @Transactional
    public int updateUser(Long id, String newUsername, String newPassword, UserType newUserType) {
        try {
            return userRepository.updateUser(id, newUsername, newPassword, newUserType);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    public List<User> findUsersByMultiConditions(String username, String userType) {
        try {
            if (username != null && userType != null) {
                return userRepository.findByUsernameAndUserType(username, userType.equals("admin")?UserType.ADMIN:UserType.USER);
            }

            if (username != null && userType == null) {
                return userRepository.findByUsername(username);
            }

            if (username == null && userType != null) {
                return userRepository.findByUserType(userType.equals("admin")?UserType.ADMIN:UserType.USER);
            }

            return userRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
