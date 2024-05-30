package com.indocyber.winterhold.dtos.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryItemDto {
    private final String name;
    private final Integer floor;
    private final String isle;
    private final String bay;
}
