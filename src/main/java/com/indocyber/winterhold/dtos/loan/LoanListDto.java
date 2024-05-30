package com.indocyber.winterhold.dtos.loan;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
public class LoanListDto {
    private final BigInteger id;
    private final String bookTitle;
    private final String customerName;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private final LocalDate returnDate;
}
