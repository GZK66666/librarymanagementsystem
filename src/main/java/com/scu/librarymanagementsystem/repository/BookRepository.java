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
}
