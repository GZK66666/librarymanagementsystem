package com.scu.librarymanagementsystem.service;

import com.scu.librarymanagementsystem.model.Book;
import com.scu.librarymanagementsystem.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public int addBook(Long isbn, String bookName, String author) {
        try {
            Book book = new Book();
            book.setIsbn(isbn);
            book.setBookName(bookName);
            book.setAuthor(author);

            bookRepository.save(book);

            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    @Transactional
    public int deleteBookByIsbn(Long isbn) {
        try {
            bookRepository.deleteById(isbn);

            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    @Transactional
    public int updateBook(Long isbn, String newBookName, String newAuthor) {
        try {
            return bookRepository.updateBook(isbn, newBookName, newAuthor);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    public List<Book> findBooksByMultiConditions(Long isbn, String bookName, String author) { // todo: 多条件模糊查询需要重构一下，太冗余了
        try {
            Specification<Book> spec = ((root, criteriaQuery, criteriaBuilder) -> {
               List<Predicate> predicates = new ArrayList<>();
               if (isbn != null) {
                   predicates.add(criteriaBuilder.equal(root.get("isbn"), isbn));
               }
               if (bookName != null) {
                   predicates.add(criteriaBuilder.equal(root.get("bookName"), bookName));
               }
               if (author != null) {
                   predicates.add(criteriaBuilder.equal(root.get("author"), author));
               }

               return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            });

            return bookRepository.findAll(spec);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
