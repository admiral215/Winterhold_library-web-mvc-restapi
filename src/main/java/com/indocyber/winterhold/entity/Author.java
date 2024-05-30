package com.indocyber.winterhold.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private BigInteger id;

    @Column(name = "Title")
    private String title;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "BirthDate")
    private LocalDate birthDate;

    @Column(name = "DeceasedDate")
    private LocalDate deceasedDate;

    @Column(name = "Education")
    private String education;

    @Column(name = "Summary")
    private String summary;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    public String getFullName(){
        return String.format("%s %s %s",this.title, this.firstName,this.lastName);
    }
}
