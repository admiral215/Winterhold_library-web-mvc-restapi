package com.indocyber.winterhold.service.impl;

import com.indocyber.winterhold.dtos.customer.*;
import com.indocyber.winterhold.entity.Customer;
import com.indocyber.winterhold.repository.CustomerRepository;
import com.indocyber.winterhold.service.CustomerService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Page<CustomerListDto> getAllBySearch(CustomerSearchDto dto, Integer pageNumber, Integer pageSize) {
        String checkedName= dto.getName() == null || dto.getName().isBlank()
                ? null : dto.getName();
        String checkedMembershipNumber= dto.getMembershipNumber() == null || dto.getMembershipNumber().isBlank()
                ? null : dto.getMembershipNumber();

        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1
                ,pageSize == null ? 10 : pageSize);

        var customerPage = customerRepository.findBySearch(pageable,checkedName,checkedMembershipNumber);

        List<CustomerListDto> customers = customerPage.stream().map(
                customer -> CustomerListDto.builder()
                        .fullName(customer.getFullName())
                        .membershipNumber(customer.getMembershipNumber())
                        .expireDate(customer.getMembershipExpireDate())
                        .build()).toList();

        return new PageImpl<>(customers,pageable,customerPage.getTotalElements());
    }

    @Override
    public void insert(CustomerInsertDto dto) {
        if (customerRepository.existsById(dto.getMembershipNumber())){
            throw new EntityExistsException("Membership number already use");
        }
        var customer = Customer.builder()
                .membershipNumber(dto.getMembershipNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .address(dto.getAddress())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .membershipExpireDate(LocalDate.now().plusYears(2))
                .build();
        customerRepository.save(customer);
    }

    @Override
    public void update(String membershipNumber, CustomerUpdateDto dto) {
        var customer = customerRepository.findById(membershipNumber).orElseThrow(() -> new EntityNotFoundException("Id Customer Not Found"));
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setBirthDate(dto.getBirthDate());
        customer.setGender(dto.getGender());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());

        customerRepository.save(customer);
    }

    @Override
    public CustomerItemDto getById(String membershipNumber) {
        var customer = customerRepository.findById(membershipNumber).orElseThrow(() -> new EntityNotFoundException("Id Customer Not Found"));
        return CustomerItemDto.builder()
                .membershipNumber(customer.getMembershipNumber())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthDate(customer.getBirthDate())
                .address(customer.getAddress())
                .gender(customer.getGender().getLabel())
                .phone(customer.getPhone())
                .membershipExpireDate(customer.getMembershipExpireDate())
                .build();
    }

    @Override
    public void extend(String membershipNumber) {
        var customer = customerRepository.findById(membershipNumber).orElseThrow(() -> new EntityNotFoundException("Id Customer Not Found"));
        customer.setMembershipExpireDate(customer.getMembershipExpireDate().plusYears(2));
        customerRepository.save(customer);
    }

    @Override
    public void delete(String membershipNumber) {
        var customer = customerRepository.findById(membershipNumber).orElseThrow(() -> new EntityNotFoundException("Id Customer Not Found"));
        customerRepository.delete(customer);
    }

    public CustomerItemDto customerItemDtoBuilder(Customer customer){
        return CustomerItemDto.builder()
                .membershipNumber(customer.getMembershipNumber())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthDate(customer.getBirthDate())
                .address(customer.getAddress())
                .gender(customer.getGender().getLabel())
                .phone(customer.getPhone())
                .membershipExpireDate(customer.getMembershipExpireDate())
                .build();
    }

    @Override
    public Page<CustomerListDto> getAllApi(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1
                ,pageSize == null ? 10 : pageSize);

        var customerPage = customerRepository.findAll(pageable);

        List<CustomerListDto> customers = customerPage.stream().map(
                customer -> CustomerListDto.builder()
                        .fullName(customer.getFullName())
                        .membershipNumber(customer.getMembershipNumber())
                        .expireDate(customer.getMembershipExpireDate())
                        .build()).toList();

        return new PageImpl<>(customers,pageable,customerPage.getTotalElements());
    }

    @Override
    public CustomerItemDto insertApi(CustomerInsertDto dto) {
        if (customerRepository.existsById(dto.getMembershipNumber())){
            throw new EntityExistsException("Membership number already use");
        }
        var customer = Customer.builder()
                .membershipNumber(dto.getMembershipNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .address(dto.getAddress())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .membershipExpireDate(LocalDate.now().plusYears(2))
                .build();
        customerRepository.save(customer);
        return customerItemDtoBuilder(customer);
    }

    @Override
    public CustomerItemDto updateApi(CustomerUpdateDto dto, String id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id Customer Not Found"));
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setBirthDate(dto.getBirthDate());
        customer.setGender(dto.getGender());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());

        customerRepository.save(customer);

        return customerItemDtoBuilder(customer);
    }

    @Override
    public CustomerItemDto deleteApi(String id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id Customer Not Found"));
        customerRepository.delete(customer);

        return customerItemDtoBuilder(customer);
    }
}
