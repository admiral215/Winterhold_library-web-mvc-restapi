package com.indocyber.winterhold.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryInsertDto {
    @NotNull
    @NotBlank
    @Size(max = 25)
    private final String name;

    @NotNull
    private final Integer floor;

    @NotNull
    @NotBlank
    @Size(max = 5)
    private final String isle;

    @NotNull
    @NotBlank
    @Size(max = 5)
    private final String bay;
}
