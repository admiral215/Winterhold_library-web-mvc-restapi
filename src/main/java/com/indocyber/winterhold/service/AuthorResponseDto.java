package com.indocyber.winterhold.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class AuthorResponseDto {
    private final String title;

    private final String firstName;

    private final String lastName;

    private final LocalDate birthDate;

    private final LocalDate deceasedDate;

    private final String education;

    private final String summary;
}
