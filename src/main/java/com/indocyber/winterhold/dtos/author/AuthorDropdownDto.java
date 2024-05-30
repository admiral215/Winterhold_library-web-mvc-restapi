package com.indocyber.winterhold.dtos.author;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class AuthorDropdownDto {
    private final BigInteger id;
    private final String name;
}
