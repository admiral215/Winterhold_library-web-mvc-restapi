package com.indocyber.winterhold.repository;

import com.indocyber.winterhold.entity.Author;
import com.indocyber.winterhold.entity.Book;
import com.indocyber.winterhold.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,String> {
    @Query("""
            SELECT b
            From Book b
            WHERE b.author.id = :id
            """)
    Page<Book> findBooksByAuthorId(Pageable pageable,
                             @RequestParam("id") BigInteger id);

    @Query("""
            SELECT b
            FROM Book b
            WHERE (:categoryId IS NULL OR b.category.name = :categoryId)
            AND (:title IS NULL OR b.title LIKE %:title%)
            AND ((:name IS NULL OR b.author.firstName LIKE %:name%)
                OR (:name IS NULL OR b.author.lastName LIKE %:name%)
                OR (:name IS NULL OR CONCAT(b.author.firstName, ' ', b.author.lastName) LIKE %:name%))
            """)
    Page<Book> findBooksByCategoryId(Pageable pageable,
                                     @RequestParam("categoryId") String categoryId,
                                     @RequestParam("title") String title,
                                     @RequestParam("name") String name);
    @Query("""
            SELECT b
            FROM Book b
            WHERE (:categoryId IS NULL OR b.category.name = :categoryId)
            """)
    Page<Book> findBooksByCategoryIdApi(Pageable pageable,
                                     @RequestParam("categoryId") String categoryId);

    @Query("""
            SELECT b
            FROM Book b
            WHERE b.isBorrowed = false
            """)
    List<Book> findByNotBorrowed();
}
