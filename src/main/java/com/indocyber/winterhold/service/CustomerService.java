package com.indocyber.winterhold.service;

import com.indocyber.winterhold.dtos.customer.*;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Page<CustomerListDto> getAllBySearch(CustomerSearchDto dto, Integer pageNumber, Integer pageSize);

    void insert(CustomerInsertDto dto);

    void update(String membershipNumber, CustomerUpdateDto dto);

    CustomerItemDto getById(String membershipNumber);

    void extend(String membershipNumber);

    void delete(String membershipNumber);

    Page<CustomerListDto> getAllApi(Integer pageNumber, Integer pageSize);

    CustomerItemDto insertApi(CustomerInsertDto dto);

    CustomerItemDto updateApi(CustomerUpdateDto dto, String id);

    CustomerItemDto deleteApi(String id);

}
