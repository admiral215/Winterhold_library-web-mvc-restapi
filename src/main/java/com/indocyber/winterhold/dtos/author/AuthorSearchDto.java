package com.indocyber.winterhold.dtos.author;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorSearchDto {
    private final String name;
}
