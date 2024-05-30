package com.indocyber.winterhold.dtos.author;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
public class AuthorItemDto {
    private final BigInteger id;

    private final String title;

    private final String firstName;

    private final String lastName;

    private final LocalDate birthDate;

    private final LocalDate deceasedDate;

    private final String education;

    private final String summary;

    public String getFullName(){
        return String.format("%s %s %s",this.title, this.firstName,this.lastName);
    }
}
