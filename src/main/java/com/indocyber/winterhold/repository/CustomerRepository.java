package com.indocyber.winterhold.repository;

import com.indocyber.winterhold.dtos.customer.CustomerDropdownDto;
import com.indocyber.winterhold.entity.Author;
import com.indocyber.winterhold.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {
    @Query("""
            SELECT c
            FROM Customer c
            WHERE (:membershipNumber IS NULL OR c.membershipNumber LIKE %:membershipNumber%)
            AND ((:name IS NULL OR c.firstName LIKE %:name%)
            OR (:name IS NULL OR c.lastName LIKE %:name%)
            OR (:name IS NULL OR CONCAT(c.firstName, ' ', c.lastName) LIKE %:name%))
            """)
    Page<Customer> findBySearch(Pageable pageable,
                              @RequestParam("name") String name,
                              @RequestParam("membershipNumber") String membershipNumber);

    @Query("""
            SELECT c
            FROM Customer c
            WHERE c.membershipExpireDate >= CURRENT_DATE
            """)
    List<Customer> findByExpireDateBeforeNow();

}
