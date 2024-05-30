package com.indocyber.winterhold.service.impl;

import com.indocyber.winterhold.dtos.author.AuthorListDto;
import com.indocyber.winterhold.dtos.book.BookItemDto;
import com.indocyber.winterhold.dtos.book.BookListDto;
import com.indocyber.winterhold.dtos.category.CategoryInsertDto;
import com.indocyber.winterhold.dtos.category.CategoryItemDto;
import com.indocyber.winterhold.dtos.category.CategoryListDto;
import com.indocyber.winterhold.entity.Category;
import com.indocyber.winterhold.repository.BookRepository;
import com.indocyber.winterhold.repository.CategoryRepository;
import com.indocyber.winterhold.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<CategoryListDto> getAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1
                ,pageSize == null ? 10 : pageSize);

        var categoryPage = categoryRepository.findAll(pageable);

        List<CategoryListDto> categories = categoryPage.stream()
                .map(category -> CategoryListDto.builder()
                        .name(category.getName())
                        .floor(category.getFloor())
                        .isle(category.getIsle())
                        .bay(category.getBay())
                        .totalBooks(category.getBooks().size())
                        .build())
                .toList();
        return new PageImpl<>(categories, pageable, categoryPage.getTotalElements());
    }

    public CategoryItemDto categoryItemDtoBuilder(Category category){
        return CategoryItemDto.builder()
                .name(category.getName())
                .floor(category.getFloor())
                .isle(category.getIsle())
                .bay(category.getBay())
                .build();
    }

    @Override
    public CategoryItemDto getById(String id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category tidak ditemukan"));

        return categoryItemDtoBuilder(category);
    }

    @Override
    public CategoryItemDto insertApi(CategoryInsertDto dto) {
        var category = Category.builder()
                .name(dto.getName())
                .floor(dto.getFloor())
                .isle(dto.getIsle())
                .bay(dto.getBay())
                .build();
        categoryRepository.save(category);

        return categoryItemDtoBuilder(category);
    }

    @Override
    public CategoryItemDto updateApi(CategoryInsertDto dto, String id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category tidak ditemukan"));
        categoryRepository.delete(category);

        var newCategory = Category.builder()
                .name(dto.getName())
                .floor(dto.getFloor())
                .isle(dto.getIsle())
                .bay(dto.getBay())
                .build();
        categoryRepository.save(newCategory);

        return categoryItemDtoBuilder(newCategory);
    }

    @Override
    public CategoryItemDto deleteByIdApi(String id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category tidak ditemukan"));
        categoryRepository.delete(category);

        return categoryItemDtoBuilder(category);
    }

    @Override
    public Page<BookListDto> getBooksById(String id) {
        Pageable pageable = PageRequest.of(0,10);
        var booksPage = bookRepository.findBooksByCategoryIdApi(pageable,id);

        List<BookListDto> books = booksPage.stream().map(book -> BookListDto.builder()
                .code(book.getCode())
                .title(book.getTitle())
                .releaseDate(book.getReleaseDate())
                .isBorrowed(book.getIsBorrowed())
                .totalPage(book.getTotalPage())

                .build()).toList();

        return new PageImpl<>(books,pageable,booksPage.getTotalElements());
    }
}
