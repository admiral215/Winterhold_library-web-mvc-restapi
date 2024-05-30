package com.indocyber.winterhold.dtos.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDropdownDto {
    private final String id;
    private final String title;
}
