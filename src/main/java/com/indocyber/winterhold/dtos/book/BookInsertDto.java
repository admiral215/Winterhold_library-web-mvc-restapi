package com.indocyber.winterhold.dtos.book;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
public class BookInsertDto {
    @NotNull
    @NotBlank
    @Size(max = 10)
    private final String code;

    @NotNull
    @NotBlank
    private final String title;

    @NotNull
    @NotBlank
    private final String categoryId;

    @NotNull
    private final BigInteger authorId;

    @NotNull
    private final LocalDate releaseDate;

    @NotNull
    @Min(10)
    @Max(1000)
    private final Integer totalPage;

    @NotNull
    @NotBlank
    private final String summary;
}
