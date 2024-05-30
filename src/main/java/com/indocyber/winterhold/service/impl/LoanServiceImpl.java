package com.indocyber.winterhold.service.impl;

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
import com.indocyber.winterhold.entity.Book;
import com.indocyber.winterhold.entity.Loan;
import com.indocyber.winterhold.repository.BookRepository;
import com.indocyber.winterhold.repository.CustomerRepository;
import com.indocyber.winterhold.repository.LoanRepository;
import com.indocyber.winterhold.service.LoanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    public LoanServiceImpl(LoanRepository loanRepository, CustomerRepository customerRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<LoanListDto> getAllBySearch(LoanSearchDto dto, Integer pageNumber, Integer pageSize) {
        String checkedBookTitle= dto.getBookTitle() == null || dto.getBookTitle().isBlank()
                ? null : dto.getBookTitle();
        String checkedCustomerName= dto.getCustomerName() == null ||dto.getCustomerName().isBlank()
                ? null : dto.getCustomerName();

        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1
                ,pageSize == null ? 10 : pageSize);

        var loanPage = loanRepository.findBySearch(pageable,checkedCustomerName,checkedBookTitle);

        List<LoanListDto> loans = loanPage.stream().map(loan -> LoanListDto.builder()
                .id(loan.getID())
                .bookTitle(loan.getBook().getTitle())
                .customerName(loan.getCustomer().getFullName())
                .loanDate(loan.getLoanDate())
                .dueDate(loan.getDueDate())
                .returnDate(loan.getReturnDate())
                .build()).toList();

        return new PageImpl<>(loans, pageable, loanPage.getTotalElements());
    }

    @Override
    public List<CustomerDropdownDto> getDropdownCustomer() {
        var customers = customerRepository.findByExpireDateBeforeNow();
        return customers.stream().map(customer -> CustomerDropdownDto.builder()
                .id(customer.getMembershipNumber())
                .name(customer.getFullName())
                .build()).toList();
    }

    @Override
    public List<BookDropdownDto> getDropdownBook() {
        var books = bookRepository.findByNotBorrowed();
        return books.stream().map(book -> BookDropdownDto.builder()
                .id(book.getCode())
                .title(book.getTitle())
                .build()).toList();
    }

    @Override
    public void insert(LoanInsertDto dto) {
        var customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not Found"));
        var book = bookRepository.findById(dto.getCodeBook()).orElseThrow(() -> new EntityNotFoundException("Book not Found"));
        if (book.getIsBorrowed()){
            throw new IllegalArgumentException("The book has borrowed");
        }
        var loan = Loan.builder()
                .book(book)
                .customer(customer)
                .loanDate(dto.getLoanDate())
                .dueDate(dto.getLoanDate().plusDays(5))
                .note(dto.getNote())
                .build();
        book.setIsBorrowed(true);
        bookRepository.save(book);
        loanRepository.save(loan);
    }

    @Override
    public LoanItemDto getById(BigInteger loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(() -> new EntityNotFoundException("Loan not Found"));
        return LoanItemDto.builder()
                .id(loan.getID())
                .codeBook(loan.getBook().getCode())
                .customerId(loan.getCustomer().getMembershipNumber())
                .loanDate(loan.getLoanDate())
                .note(loan.getNote())
                .build();
    }

    @Override
    public void update(LoanInsertDto dto, BigInteger loanId) {
        var customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not Found"));
        var book = bookRepository.findById(dto.getCodeBook()).orElseThrow(() -> new EntityNotFoundException("Book not Found"));
        if (book.getIsBorrowed()){
            throw new IllegalArgumentException("The book has borrowed");
        }
        var loan = loanRepository.findById(loanId).orElseThrow(() -> new EntityNotFoundException("Loan not Found"));

        //set isBorrowed book yang sebelum di update.
        setIsBorrowedToFalse(loan.getBook().getCode());

        book.setIsBorrowed(true);
        loan.setBook(book);
        loan.setCustomer(customer);
        loan.setLoanDate(dto.getLoanDate());
        loan.setDueDate(dto.getLoanDate().plusDays(5));
        loan.setNote(dto.getNote());

        loanRepository.save(loan);
    }

    @Override
    public void delete(BigInteger loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(() -> new EntityNotFoundException("Loan not Found"));

        //set isBorrowed book yang sebelum di delete.
        setIsBorrowedToFalse(loan.getBook().getCode());

        loanRepository.delete(loan);
    }

    @Override
    public void returnBook(BigInteger loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(() -> new EntityNotFoundException("Loan not Found"));

        //set isBorrowed book yang sebelum di return.
        setIsBorrowedToFalse(loan.getBook().getCode());

        //return buku
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }

    @Override
    public CustomerItemDto getCustomer(BigInteger loanId) {
        var customer = loanRepository.findById(loanId).orElseThrow(() -> new EntityNotFoundException("Loan not Found")).getCustomer();

        return CustomerItemDto.builder()
                .membershipNumber(customer.getMembershipNumber())
                .phone(customer.getPhone())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .membershipExpireDate(customer.getMembershipExpireDate())
                .build();
    }

    @Override
    public BookAndCategoryDetailDto getBook(BigInteger loanId) {
        var book = loanRepository.findById(loanId).orElseThrow(() -> new EntityNotFoundException("Loan not Found")).getBook();
        return BookAndCategoryDetailDto.builder()
                .title(book.getTitle())
                .category(book.getCategory().getName())
                .author(book.getAuthor().getFullName())
                .floor(book.getCategory().getFloor())
                .isle(book.getCategory().getIsle())
                .bay(book.getCategory().getBay())
                .build();
    }

    public LoanItemDto loanItemDtoBuilder(Loan loan){
        return LoanItemDto.builder()
                .id(loan.getID())
                .codeBook(loan.getBook().getCode())
                .customerId(loan.getCustomer().getMembershipNumber())
                .loanDate(loan.getLoanDate())
                .note(loan.getNote())
                .build();
    }

    @Override
    public Page<LoanListDto> getAllApi(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1
                ,pageSize == null ? 10 : pageSize);

        var loanPage = loanRepository.findAll(pageable);

        List<LoanListDto> loans = loanPage.stream().map(loan -> LoanListDto.builder()
                .id(loan.getID())
                .bookTitle(loan.getBook().getTitle())
                .customerName(loan.getCustomer().getFullName())
                .loanDate(loan.getLoanDate())
                .dueDate(loan.getDueDate())
                .returnDate(loan.getReturnDate())
                .build()).toList();

        return new PageImpl<>(loans, pageable, loanPage.getTotalElements());
    }

    @Override
    public LoanItemDto insertApi(LoanInsertDto dto) {
        var customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not Found"));
        var book = bookRepository.findById(dto.getCodeBook()).orElseThrow(() -> new EntityNotFoundException("Book not Found"));
        if (book.getIsBorrowed()){
            throw new IllegalArgumentException("The book has borrowed");
        }
        var loan = Loan.builder()
                .book(book)
                .customer(customer)
                .loanDate(dto.getLoanDate())
                .dueDate(dto.getLoanDate().plusDays(5))
                .note(dto.getNote())
                .build();
        book.setIsBorrowed(true);
        bookRepository.save(book);
        loanRepository.save(loan);

        return loanItemDtoBuilder(loan);
    }

    @Override
    public LoanItemDto updateApi(LoanInsertDto dto, BigInteger id) {
        var customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not Found"));
        var book = bookRepository.findById(dto.getCodeBook()).orElseThrow(() -> new EntityNotFoundException("Book not Found"));
        if (book.getIsBorrowed()){
            throw new IllegalArgumentException("The book has borrowed");
        }
        var loan = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan not Found"));

        //set isBorrowed book yang sebelum di update.
        setIsBorrowedToFalse(loan.getBook().getCode());

        book.setIsBorrowed(true);
        loan.setBook(book);
        loan.setCustomer(customer);
        loan.setLoanDate(dto.getLoanDate());
        loan.setDueDate(dto.getLoanDate().plusDays(5));
        loan.setNote(dto.getNote());

        loanRepository.save(loan);
        return loanItemDtoBuilder(loan);
    }

    @Override
    public LoanItemDto deleteApi(BigInteger id) {
        var loan = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan not Found"));

        //set isBorrowed book yang sebelum di delete.
        setIsBorrowedToFalse(loan.getBook().getCode());

        loanRepository.delete(loan);
        return loanItemDtoBuilder(loan);
    }

    public void setIsBorrowedToFalse(String codeBook){
        var currentBook = bookRepository.findById(codeBook).orElseThrow(() -> new EntityNotFoundException("Book not Found"));
        currentBook.setIsBorrowed(false);
        bookRepository.save(currentBook);
    }
}
