package com.indocyber.winterhold.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @Column(name = "Name")
    private String name;

    @Column(name = "Floor")
    private Integer floor;

    @Column(name = "Isle")
    private String isle;

    @Column(name = "Bay")
    private String bay;

    @OneToMany(mappedBy = "category")
    private List<Book> books;
}
