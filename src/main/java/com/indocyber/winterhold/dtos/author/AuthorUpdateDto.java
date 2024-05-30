package com.indocyber.winterhold.dtos.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AuthorUpdateDto {
    @NotNull
    @NotBlank
    @Size(max = 50)
    private final String title;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private final String firstName;

    @Size(max = 50)
    private final String lastName;

    @NotNull
    private final LocalDate birthDate;

    private final LocalDate deceasedDate;

    private final String education;

    @Size(max = 500)
    private final String summary;
}
