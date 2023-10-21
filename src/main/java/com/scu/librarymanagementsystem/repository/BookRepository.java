package com.scu.librarymanagementsystem.repository;

import com.scu.librarymanagementsystem.model.Book;
import com.scu.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Modifying
    @Query("UPDATE Book b SET b.bookName = :newBookName, b.author = :newAuthor WHERE b.isbn = :isbn")
    int updateBook(Long isbn, String newBookName, String newAuthor);

    List<Book> findByIsbn(Long isbn);

    List<Book> findByBookName(String bookName);

    List<Book> findByAuthor(String author);

    List<Book> findByIsbnAndBookName(Long isbn, String bookName);

    List<Book> findByIsbnAndAuthor(Long isbn, String author);

    List<Book> findByBookNameAndAuthor(String bookName, String author);

    List<Book> findByIsbnAndBookNameAndAuthor(Long isbn, String bookName, String author);
}
