package com.indocyber.winterhold.service.impl;

import com.indocyber.winterhold.dtos.author.*;
import com.indocyber.winterhold.dtos.book.BookListDto;
import com.indocyber.winterhold.entity.Author;
import com.indocyber.winterhold.repository.AuthorRepository;
import com.indocyber.winterhold.repository.BookRepository;
import com.indocyber.winterhold.service.AuthorResponseDto;
import com.indocyber.winterhold.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void insert(AuthorInsertDto dto) {
        var author = Author.builder()
                .title(dto.getTitle())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .deceasedDate(dto.getDeceasedDate())
                .education(dto.getEducation())
                .summary(dto.getSummary())
                .build();
        authorRepository.save(author);
    }

    @Override
    public AuthorItemDto insertApi(AuthorInsertDto dto) {
        var author = Author.builder()
                .title(dto.getTitle())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .deceasedDate(dto.getDeceasedDate())
                .education(dto.getEducation())
                .summary(dto.getSummary())
                .build();
        authorRepository.save(author);
        return authorItemDtoBuilder(author);
    }

    @Override
    public Page<AuthorListDto> getBySearch(AuthorSearchDto dto, Integer pageNumber, Integer pageSize) {
        String checkedName= dto.getName() == null || dto.getName().isBlank()
                ? null : dto.getName();


        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1
                ,pageSize == null ? 10 : pageSize);

        var authorsPage = authorRepository.findBySearch(pageable,checkedName);

        List<AuthorListDto> authorListDto =authorsPage.stream()
                .map(author -> AuthorListDto.builder()
                        .id(author.getId())
                        .fullName(author.getFullName())
                        .birthDate(author.getBirthDate())
                        .deceaseDate(author.getDeceasedDate())
                        .education(author.getEducation())
                        .build())
                .toList();
        return new PageImpl<>(authorListDto, pageable, authorsPage.getTotalElements());
    }

    @Override
    public AuthorItemDto getById(BigInteger id) {
        var author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id Author Not Found"));

        return authorItemDtoBuilder(author);
    }

    public AuthorItemDto authorItemDtoBuilder(Author author){
        return AuthorItemDto.builder()
                .id(author.getId())
                .title(author.getTitle())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .deceasedDate(author.getDeceasedDate())
                .education(author.getEducation())
                .summary(author.getSummary())
                .build();
    }

    @Override
    public void update(BigInteger id, AuthorUpdateDto dto) {
        var author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id Author Not Found"));
        author.setTitle(dto.getTitle());
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setBirthDate(dto.getBirthDate());
        author.setDeceasedDate(dto.getDeceasedDate());
        author.setEducation(dto.getEducation());
        author.setSummary(dto.getSummary());
        authorRepository.save(author);
    }

    @Override
    public Page<BookListDto> getBooksById(BigInteger id) {
        Pageable pageable = PageRequest.of(0,10);
        var booksPage = bookRepository.findBooksByAuthorId(pageable,id);

        List<BookListDto> books = booksPage.stream().map(book -> BookListDto.builder()
                .code(book.getCode())
                .title(book.getTitle())
                .category(book.getCategory().getName())
                .releaseDate(book.getReleaseDate())
                .isBorrowed(book.getIsBorrowed())
                .totalPage(book.getTotalPage())
                .author(book.getAuthor().getFullName())
                .build()).toList();

        return new PageImpl<>(books,pageable,booksPage.getTotalElements());
    }

    @Override
    public void deleteById(BigInteger id) {
        var author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id Author Not Found"));
        authorRepository.delete(author);
    }

    @Override
    public Page<AuthorListDto> getAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1
                ,pageSize == null ? 10 : pageSize);

        var authorsPage = authorRepository.findAll(pageable);

        List<AuthorListDto> authorListDto =authorsPage.stream()
                .map(author -> AuthorListDto.builder()
                        .id(author.getId())
                        .fullName(author.getFullName())
                        .birthDate(author.getBirthDate())
                        .deceaseDate(author.getDeceasedDate())
                        .education(author.getEducation())
                        .build())
                .toList();
        return new PageImpl<>(authorListDto, pageable, authorsPage.getTotalElements());
    }

    @Override
    public AuthorItemDto updateApi(AuthorUpdateDto dto, BigInteger id) {
        var author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id Author Not Found"));
        author.setTitle(dto.getTitle());
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setBirthDate(dto.getBirthDate());
        author.setDeceasedDate(dto.getDeceasedDate());
        author.setEducation(dto.getEducation());
        author.setSummary(dto.getSummary());
        authorRepository.save(author);

        return authorItemDtoBuilder(author);
    }

    @Override
    public AuthorItemDto deleteByIdApi(BigInteger id) {
        var author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id Author Not Found"));
        authorRepository.delete(author);
        return authorItemDtoBuilder(author);
    }
}
