package com.indocyber.winterhold.dtos.book;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
public class BookUpdateDto {
    private final String title;
    private final String categoryId;
    private final BigInteger authorId;
    private final LocalDate releaseDate;
    private final Integer totalPage;
    private final String summary;
}
