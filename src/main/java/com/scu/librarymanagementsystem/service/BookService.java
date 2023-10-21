package com.scu.librarymanagementsystem.service;

import com.scu.librarymanagementsystem.common.utils.RedisUtil;
import com.scu.librarymanagementsystem.model.Book;
import com.scu.librarymanagementsystem.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RedisUtil redisUtil;

    private final Long BOOK_REDIS_TIMEOUT = 30L;

    private final TimeUnit BOOK_REDIS_TIMEOUT_UNIT = TimeUnit.MINUTES;

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
            List<Book> oldBook = bookRepository.findByIsbn(isbn);
            if (oldBook != null && !oldBook.isEmpty()) {
                bookRepository.deleteById(isbn);
                redisUtil.del(generateAllCacheKeys(oldBook.get(0).getIsbn(), oldBook.get(0).getBookName(), oldBook.get(0).getAuthor()).toArray(new String[0]));
            }

            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    @Transactional
    public int updateBook(Long isbn, String newBookName, String newAuthor) {
        try {
            List<Book> oldBook = bookRepository.findByIsbn(isbn);
            if (oldBook != null && !oldBook.isEmpty()) {
                bookRepository.updateBook(isbn, newBookName, newAuthor);
                redisUtil.del(generateAllCacheKeys(oldBook.get(0).getIsbn(), oldBook.get(0).getBookName(), oldBook.get(0).getAuthor()).toArray(new String[0]));
            }

            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    public List<Book> findBooksByMultiConditions(Long isbn, String bookName, String author) {
        try {
            String cacheKey = generateCacheKey(isbn, bookName, author);
            List<Book> cacheResult = (List<Book>) redisUtil.get(cacheKey);
            if (cacheResult != null && !cacheResult.isEmpty()) {
                return cacheResult;
            }

            List<Book> dbResult = findBooksByDB(isbn, bookName, author);
            redisUtil.setWithExpiration(cacheKey, dbResult, BOOK_REDIS_TIMEOUT, BOOK_REDIS_TIMEOUT_UNIT);

            return dbResult;
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<String> generateAllCacheKeys(Long isbn, String bookName, String author) {
        return new ArrayList<String>() {{
            add(generateCacheKey(isbn, null, null));
            add(generateCacheKey(null, bookName, null));
            add(generateCacheKey(null, null, author));
            add(generateCacheKey(isbn, bookName, null));
            add(generateCacheKey(isbn, null, author));
            add(generateCacheKey(null, bookName, author));
            add(generateCacheKey(isbn, bookName, author));
        }};
    }

    private String generateCacheKey(Long isbn, String bookName, String author) {
        String key = "book:";
        if (isbn != null) {
            key += "isbn_" + isbn;
        }
        if (bookName != null) {
            key += "bookName_" + bookName;
        }
        if (author != null) {
            key += "author_" + author;
        }

        return key;
    }

    private List<Book> findBooksByDB(Long isbn, String bookName, String author) {
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
    }
}
