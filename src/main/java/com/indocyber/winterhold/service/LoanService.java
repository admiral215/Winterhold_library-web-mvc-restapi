package com.indocyber.winterhold.service;

import com.indocyber.winterhold.dtos.book.BookAndCategoryDetailDto;
import com.indocyber.winterhold.dtos.book.BookDropdownDto;
import com.indocyber.winterhold.dtos.book.BookItemDto;
import com.indocyber.winterhold.dtos.book.BookListDto;
import com.indocyber.winterhold.dtos.customer.CustomerDropdownDto;
import com.indocyber.winterhold.dtos.customer.CustomerItemDto;
import com.indocyber.winterhold.dtos.loan.LoanInsertDto;
import com.indocyber.winterhold.dtos.loan.LoanItemDto;
import com.indocyber.winterhold.dtos.loan.LoanListDto;
import com.indocyber.winterhold.dtos.loan.LoanSearchDto;
import org.springframework.data.domain.Page;

import java.math.BigInteger;
import java.util.List;

public interface LoanService {
    Page<LoanListDto> getAllBySearch(LoanSearchDto dto, Integer pageNumber, Integer pageSize);

    List<CustomerDropdownDto> getDropdownCustomer();

    List<BookDropdownDto> getDropdownBook();

    void insert(LoanInsertDto dto);

    LoanItemDto getById(BigInteger loanId);

    void update(LoanInsertDto dto, BigInteger loanId);

    void delete(BigInteger loanId);

    void returnBook(BigInteger loanId);

    CustomerItemDto getCustomer(BigInteger loanId);

    BookAndCategoryDetailDto getBook(BigInteger loanId);

    Page<LoanListDto> getAllApi(Integer pageNumber, Integer pageSize);

    LoanItemDto insertApi(LoanInsertDto dto);

    LoanItemDto updateApi(LoanInsertDto dto, BigInteger id);

    LoanItemDto deleteApi(BigInteger id);
}
