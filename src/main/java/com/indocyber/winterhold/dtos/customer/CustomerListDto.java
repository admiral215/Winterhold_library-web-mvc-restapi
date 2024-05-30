package com.indocyber.winterhold.dtos.customer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerListDto {
    private final String membershipNumber;
    private final String fullName;
    private final LocalDate expireDate;
}
