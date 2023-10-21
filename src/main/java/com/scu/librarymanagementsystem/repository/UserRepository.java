package com.scu.librarymanagementsystem.repository;

import com.scu.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> { // Long需要和数据库表的主键类型一致
    @Modifying
    @Query("UPDATE User u SET u.username = :newUsername, u.password = :newPassword, u.userType = :newUserType WHERE u.id = :id")
    int updateUser(Long id, String newUsername, String newPassword, String newUserType);
}
