package com.indocyber.winterhold.repository;

import com.indocyber.winterhold.entity.Author;
import com.indocyber.winterhold.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

public interface AuthorRepository extends JpaRepository<Author, BigInteger> {
    @Query("""
            SELECT a
            FROM Author a
            WHERE (:name IS NULL OR a.firstName LIKE %:name%)
            OR (:name IS NULL OR a.lastName LIKE %:name%)
            OR (:name IS NULL OR CONCAT(a.firstName, ' ', a.lastName) LIKE %:name%)
            """)
    Page<Author> findBySearch(Pageable pageable,
                              @RequestParam("name") String name);


}
