package com.indocyber.winterhold.dtos.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookSearchDto {
    private final String title;
    private final String authorName;
}
