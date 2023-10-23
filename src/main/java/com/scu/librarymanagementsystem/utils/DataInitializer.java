package com.scu.librarymanagementsystem.utils;

import com.scu.librarymanagementsystem.model.Book;
import com.scu.librarymanagementsystem.model.User;
import com.scu.librarymanagementsystem.repository.BookRepository;
import com.scu.librarymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner { // SpringBoot启动时，会查找并执行所有实现了CommandLineRunner接口的bean的run方法
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        // 生成并插入用户示例数据
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("123456");
        admin.setUserType("admin");
        userRepository.save(admin);

        User user = new User();
        user.setUsername("user");
        user.setPassword("123456");
        user.setUserType("user");
        userRepository.save(user);

        User user1 = new User();
        user1.setUsername("张三");
        user1.setPassword("123456");
        user1.setUserType("user");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("李四");
        user2.setPassword("123456");
        user2.setUserType("user");
        userRepository.save(user2);

        // 生成并插入图书示例数据
        Book book1 = new Book();
        book1.setIsbn(1L);
        book1.setBookName("红楼梦");
        book1.setAuthor("曹雪芹");
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setIsbn(2L);
        book2.setBookName("西游记");
        book2.setAuthor("吴承恩");
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setIsbn(3L);
        book3.setBookName("三国演义");
        book3.setAuthor("罗贯中");
        bookRepository.save(book3);

        Book book4 = new Book();
        book4.setIsbn(4L);
        book4.setBookName("水浒传");
        book4.setAuthor("施耐庵");
        bookRepository.save(book4);

        Book book5 = new Book();
        book5.setIsbn(5L);
        book5.setBookName("Java编程思想");
        book5.setAuthor("Bruce Eckel");
        bookRepository.save(book5);
    }
}
