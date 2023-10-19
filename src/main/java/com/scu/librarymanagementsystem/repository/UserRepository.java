package com.scu.librarymanagementsystem.repository;

import com.scu.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> { // Long需要和数据库表的主键类型一致
    @Query("SELECT u FROM User u WHERE u.username = :username") // 自定义查询
    User findByUsername(String username);
}
