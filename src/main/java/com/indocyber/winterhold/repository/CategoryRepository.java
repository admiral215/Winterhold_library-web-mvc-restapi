package com.indocyber.winterhold.repository;

import com.indocyber.winterhold.entity.Author;
import com.indocyber.winterhold.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

public interface CategoryRepository extends JpaRepository<Category,String> {

    @Query("""
            SELECT c
            FROM Category c
            WHERE (:name IS NULL OR c.name LIKE %:name%)
            """)
    Page<Category> findBySearch(Pageable pageable,
                              @RequestParam("name") String name);
}
