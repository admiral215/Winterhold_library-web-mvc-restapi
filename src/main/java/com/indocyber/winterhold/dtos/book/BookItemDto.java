package com.indocyber.winterhold.dtos.book;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
public class BookItemDto {
    private final String code;
    private final String title;
    private final String categoryId;
    private final Boolean isBorrowed;
    private final LocalDate releaseDate;
    private final Integer totalPage;
    private final BigInteger authorId;
    private final String summary;
}
