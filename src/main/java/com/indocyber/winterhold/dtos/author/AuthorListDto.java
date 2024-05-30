package com.indocyber.winterhold.dtos.author;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Builder
public class AuthorListDto {
    private final BigInteger id;
    private final String fullName;
    private final LocalDate birthDate;
    private final LocalDate deceaseDate;
    private final String education;

    public String getStatus(){
        if (deceaseDate != null){
            return "Deceased";
        }
        return "Alive";
    }

    public Long getAge(){
        return ChronoUnit.YEARS.between(birthDate,LocalDate.now());
    }
}
