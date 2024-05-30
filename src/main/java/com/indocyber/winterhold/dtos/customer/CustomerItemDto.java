package com.indocyber.winterhold.dtos.customer;

import com.indocyber.winterhold.entity.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerItemDto {
    private String membershipNumber;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String gender;

    private String phone;

    private String address;

    private LocalDate membershipExpireDate;

    public String getFullName(){
        return String.format("%s %s", this.firstName,this.lastName);
    }
}
