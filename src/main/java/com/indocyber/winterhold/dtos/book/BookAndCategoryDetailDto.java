package com.indocyber.winterhold.dtos.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookAndCategoryDetailDto {
    private final String title;
    private final String author;
    private final String category;
    private final Integer floor;
    private final String isle;
    private final String bay;
}
