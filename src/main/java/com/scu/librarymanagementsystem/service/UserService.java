package com.scu.librarymanagementsystem.service;

import com.scu.librarymanagementsystem.common.enums.UserType;
import com.scu.librarymanagementsystem.model.User;
import com.scu.librarymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Long addUser(String userName, String passWord, UserType userType) {
        try {
            User user = new User();

            user.setUsername(userName);
            user.setPassword(passWord);
            user.setUserType(userType);

            User savedUser = userRepository.save(user);

            return savedUser.getId();
        } catch (Exception e) {
            return (long) -1;
        }
    }

    @Transactional
    public void deleteUser(String userName) {
        userRepository.deleteUsersByUsername(userName);
    }

    @Transactional
    public int updateUserName(String oldUserName, String newUserName) {
        return userRepository.updateUsername(oldUserName, newUserName);
    }

    @Transactional
    public int updatePassword(String username, String newPassword) {
        return userRepository.updatePassword(username, newPassword);
    }

    @Transactional
    public int updateUserType(String username, UserType userType) {
        return userRepository.updateUserType(username, userType);
    }

    public List<User> findUsersByUserName(String userName) {
        return userRepository.findUsersByUsername(userName);
    }

    public List<User> findUsersByUserType(UserType userType) {
        return userRepository.findUsersByUserType(userType);
    }

    public List<User> findUsersByUserNameAndUserType(String username, UserType userType) {
        return userRepository.findUsersByUserNameAndUserType(username, userType);
    }
}
