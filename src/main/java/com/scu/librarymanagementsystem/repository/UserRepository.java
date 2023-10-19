package com.scu.librarymanagementsystem.repository;

import com.scu.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { // Long需要和数据库表的主键类型一致
}
