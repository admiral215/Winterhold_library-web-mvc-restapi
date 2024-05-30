package com.indocyber.winterhold.dtos.customer;

import com.indocyber.winterhold.entity.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerUpdateDto {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Gender gender;

    private String phone;

    private String address;
}
