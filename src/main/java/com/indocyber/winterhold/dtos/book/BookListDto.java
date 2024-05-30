package com.indocyber.winterhold.dtos.book;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookListDto {
    private final String code;
    private final String title;
    private final String category;
    private final Boolean isBorrowed;
    private final LocalDate releaseDate;
    private final Integer totalPage;
    private final String author;
    private final String summary;

    public String getLabelBorrowed(){
        if (isBorrowed){
            return "Borrowed";
        }
        return "Available";
    }
}
