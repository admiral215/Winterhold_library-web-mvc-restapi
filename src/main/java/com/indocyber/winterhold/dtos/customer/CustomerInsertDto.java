package com.indocyber.winterhold.dtos.customer;

import com.indocyber.winterhold.entity.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerInsertDto {
    @NotNull
    @NotBlank
    @Size(max = 10)
    private String membershipNumber;

    @NotNull
    @NotBlank
    @Size(max = 25)
    private String firstName;

    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Gender gender;

    @NotNull
    @NotBlank
    @Size(min = 7,max = 13)
    private String phone;

    @NotNull
    @NotBlank
    @Size(max = 500)
    private String address;
}
