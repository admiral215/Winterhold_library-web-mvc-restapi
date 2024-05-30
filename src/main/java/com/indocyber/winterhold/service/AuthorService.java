package com.indocyber.winterhold.service;

import com.indocyber.winterhold.dtos.author.*;
import com.indocyber.winterhold.dtos.book.BookListDto;
import com.indocyber.winterhold.entity.Author;
import org.springframework.data.domain.Page;

import java.math.BigInteger;

public interface AuthorService {
    void insert(AuthorInsertDto dto);
    AuthorItemDto insertApi(AuthorInsertDto dto);

    Page<AuthorListDto> getBySearch(AuthorSearchDto dto, Integer pageNumber ,Integer pageSize);

    AuthorItemDto getById(BigInteger id);

    void update(BigInteger id, AuthorUpdateDto dto);

    Page<BookListDto> getBooksById(BigInteger id);

    void deleteById(BigInteger id);

    Page<AuthorListDto> getAll(Integer pageNumber, Integer pageSize);

    AuthorItemDto updateApi(AuthorUpdateDto dto, BigInteger id);

    AuthorItemDto deleteByIdApi(BigInteger id);
}
