package com.indocyber.winterhold.dtos.loan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoanInsertDto {
    @NotBlank
    @NotNull
    private final String customerId;

    @NotNull
    @NotBlank
    private final String codeBook;

    @NotNull
    private final LocalDate loanDate;

    @NotNull
    @NotBlank
    private final String note;
}
