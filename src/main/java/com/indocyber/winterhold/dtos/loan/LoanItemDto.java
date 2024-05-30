package com.indocyber.winterhold.dtos.loan;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
public class LoanItemDto {
    private final BigInteger id;
    private final String customerId;
    private final String codeBook;
    private final LocalDate loanDate;
    private final String note;
}
