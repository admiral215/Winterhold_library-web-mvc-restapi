package com.indocyber.winterhold.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Books")
public class Book {
    @Id
    @Column(name = "Code")
    private String code;

    @Column(name = "Title")
    private String title;

    @Column(name = "IsBorrowed")
    private Boolean isBorrowed;

    @Column(name = "Summary")
    private String summary;

    @Column(name = "ReleaseDate")
    private LocalDate releaseDate;

    @Column(name = "TotalPage")
    private Integer totalPage;

    @ManyToOne
    @JoinColumn(name = "AuthorID")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "CategoryName")
    private Category category;

    @OneToMany(mappedBy = "book")
    private List<Loan> loans;
}
