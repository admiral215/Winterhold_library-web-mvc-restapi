package com.indocyber.winterhold.service;

import com.indocyber.winterhold.dtos.author.AuthorDropdownDto;
import com.indocyber.winterhold.dtos.book.*;
import com.indocyber.winterhold.dtos.category.CategoryInsertDto;
import com.indocyber.winterhold.dtos.category.CategoryListDto;
import com.indocyber.winterhold.dtos.category.CategorySearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    Page<BookListDto> getAllByCategoryId(BookSearchDto dto, String categoryName, Integer pageNumber, Integer pageSize);

    Page<CategoryListDto> getAllBySearch(CategorySearchDto dto, Integer pageNumber, Integer pageSize);

    CategoryListDto getCategoryById(String categoryId);

    void insert(CategoryInsertDto dto);

    void update(CategoryInsertDto dto, String categoryId);

    void delete(String categoryId);

    List<AuthorDropdownDto> getDropdownAuthor();

    void insertBook(String categoryId, BookInsertDto dto);

    BookItemDto getBookById(String bookId);

    void updateBook(String bookId, BookUpdateDto dto);

    void deleteBook(String bookId);

    BookItemDto insertBookApi(BookInsertDto dto);

    BookItemDto updateBookApi(BookUpdateDto dto, String id);

    BookItemDto deleteBookApi(String bookId);

    Boolean isExistBook(String id);
}
