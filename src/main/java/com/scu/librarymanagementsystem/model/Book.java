package com.scu.librarymanagementsystem.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@ApiModel("图书信息")
public class Book {
    @Id
    @ApiModelProperty("图书编号")
    private Long isbn;

    @Column(unique = true)
    @ApiModelProperty("书名")
    private String bookName;

    @ApiModelProperty("作者")
    private String author;

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
