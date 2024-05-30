package com.indocyber.winterhold.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @Column(name = "MembershipNumber")
    private String membershipNumber;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "BirthDate")
    private LocalDate birthDate;

    @Column(name = "Gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Address")
    private String address;

    @Column(name = "MembershipExpireDate")
    private LocalDate membershipExpireDate;

    @OneToMany(mappedBy = "customer")
    private List<Loan> loans;

    public String getFullName(){
        return String.format("%s %s", this.firstName,this.lastName);
    }
}
