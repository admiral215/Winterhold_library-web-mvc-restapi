package com.indocyber.winterhold.dtos.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryListDto {
    private final String name;
    private final Integer floor;
    private final String isle;
    private final String bay;
    private final Integer totalBooks;

}
