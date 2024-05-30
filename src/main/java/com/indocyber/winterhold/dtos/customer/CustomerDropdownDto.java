package com.indocyber.winterhold.dtos.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDropdownDto {
    private final String id;
    private final String name;
}
