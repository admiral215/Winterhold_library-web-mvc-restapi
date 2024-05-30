package com.indocyber.winterhold.repository;

import com.indocyber.winterhold.entity.Customer;
import com.indocyber.winterhold.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

public interface LoanRepository extends JpaRepository<Loan, BigInteger> {
    @Query("""
            SELECT l
            FROM Loan l
            WHERE (:bookTitle IS NULL OR l.book.title LIKE %:bookTitle%)
            AND ((:customerName IS NULL OR l.customer.firstName LIKE %:customerName%)
            OR (:customerName IS NULL OR l.customer.lastName LIKE %:customerName%)
            OR (:customerName IS NULL OR CONCAT(l.customer.firstName, ' ', l.customer.lastName) LIKE %:customerName%))
            """)
    Page<Loan> findBySearch(Pageable pageable,
                                @RequestParam("customerName") String customerName,
                                @RequestParam("bookTitle") String bookTitle);

}
