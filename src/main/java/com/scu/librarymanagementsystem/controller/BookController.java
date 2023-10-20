package com.scu.librarymanagementsystem.controller;

import com.scu.librarymanagementsystem.model.Book;
import com.scu.librarymanagementsystem.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Books")
@Api(tags = "图书管理")
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
    public List<Book> findBooks(@RequestParam(required = false) Long isbn, @RequestParam(required = false) String bookName, @RequestParam(required = false) String author) {
        return bookService.findBooksByMultiConditions(isbn, bookName, author);
    }
}
