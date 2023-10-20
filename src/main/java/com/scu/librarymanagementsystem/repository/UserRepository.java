package com.scu.librarymanagementsystem.repository;

import com.scu.librarymanagementsystem.common.enums.UserType;
import com.scu.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> { // Long需要和数据库表的主键类型一致
    @Query("SELECT u FROM User u WHERE u.username = :username") // 自定义查询
    List<User> findUsersByUsername(String username);

    @Query("select u from User u where u.userType = :userType")
    List<User> findUsersByUserType(UserType userType);

    @Query("select u from User u where u.username = :username and u.userType = :userType")
    List<User> findUsersByUserNameAndUserType(String username, UserType userType);

    void deleteUsersByUsername(String username); // 这里方法名还有讲究，By后面接的属性名必须和实体类的属性名一致才行。。。。

    @Modifying
    @Query("UPDATE User u SET u.username = :newUsername WHERE u.username = :oldUsername")
    int updateUsername(String oldUsername, String newUsername);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.username = :username")
    int updatePassword(String username, String newPassword);

    @Modifying
    @Query("UPDATE User u SET u.userType = :newUserType WHERE u.username = :username")
    int updateUserType(String username, UserType newUserType);
}
