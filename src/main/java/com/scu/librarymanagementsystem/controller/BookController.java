package com.scu.librarymanagementsystem.controller;

import com.scu.librarymanagementsystem.model.Book;
import com.scu.librarymanagementsystem.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/Books")
@Api(tags = "图书管理")
@CrossOrigin(origins = "http://localhost:63342") // 允许跨域请求
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    @ApiOperation("新增图书")
    public int addBook(@RequestParam Long isbn, @RequestParam String bookName, @RequestParam String author) {
        return bookService.addBook(isbn, bookName, author);
    }

    @GetMapping("delete")
    @ApiOperation("删除图书")
    public int deleteBook(@RequestParam Long isbn) {
        return bookService.deleteBookByIsbn(isbn);
    }

    @GetMapping("/updateBook")
    @ApiOperation("更改图书信息")
    public int updateBook(@RequestParam Long isbn, @RequestParam String bookName, @RequestParam String author) {
        return bookService.updateBook(isbn, bookName, author);
    }

    @GetMapping("/findBooks")
    @ApiOperation("查询图书")
    public Map<String, Object> findBooks(@RequestParam(required = false) Long isbn, @RequestParam(required = false) String bookName, @RequestParam(required = false) String author) {
        List<Book> books = bookService.findBooksByMultiConditions(isbn, bookName, author);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "");
        response.put("count", books.size());
        response.put("data", books);

        return response;
    }
}
