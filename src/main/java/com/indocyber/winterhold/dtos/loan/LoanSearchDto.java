package com.indocyber.winterhold.dtos.loan;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class LoanSearchDto {
    private final String bookTitle;
    private final String customerName;
}
