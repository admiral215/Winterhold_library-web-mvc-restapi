package com.indocyber.winterhold.dtos.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSearchDto {
    private final String membershipNumber;
    private final String name;
}
