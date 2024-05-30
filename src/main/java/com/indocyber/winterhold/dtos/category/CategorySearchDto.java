package com.indocyber.winterhold.dtos.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategorySearchDto {
    private final String name;
}
