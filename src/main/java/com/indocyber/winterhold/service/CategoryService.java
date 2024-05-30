package com.indocyber.winterhold.service;

import com.indocyber.winterhold.dtos.book.BookItemDto;
import com.indocyber.winterhold.dtos.book.BookListDto;
import com.indocyber.winterhold.dtos.category.CategoryInsertDto;
import com.indocyber.winterhold.dtos.category.CategoryItemDto;
import com.indocyber.winterhold.dtos.category.CategoryListDto;
import org.springframework.data.domain.Page;

import java.math.BigInteger;

public interface CategoryService {
    Page<CategoryListDto> getAll(Integer pageNumber, Integer pageSize);

    CategoryItemDto getById(String id);

    CategoryItemDto insertApi(CategoryInsertDto dto);

    CategoryItemDto updateApi(CategoryInsertDto dto, String id);

    CategoryItemDto deleteByIdApi(String id);

    Page<BookListDto> getBooksById(String id);
}
