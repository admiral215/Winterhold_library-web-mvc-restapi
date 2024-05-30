package com.indocyber.winterhold.service.impl;

import com.indocyber.winterhold.dtos.author.AuthorDropdownDto;
import com.indocyber.winterhold.dtos.book.*;
import com.indocyber.winterhold.dtos.category.CategoryInsertDto;
import com.indocyber.winterhold.dtos.category.CategoryListDto;
import com.indocyber.winterhold.dtos.category.CategorySearchDto;
import com.indocyber.winterhold.entity.Book;
import com.indocyber.winterhold.entity.Category;
import com.indocyber.winterhold.repository.AuthorRepository;
import com.indocyber.winterhold.repository.BookRepository;
import com.indocyber.winterhold.repository.CategoryRepository;
import com.indocyber.winterhold.service.BookService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<BookListDto> getAllByCategoryId(BookSearchDto dto,String categoryId,  Integer pageNumber, Integer pageSize) {
        String checkedTitle = dto.getTitle() == null || dto.getTitle().isBlank()
                ? null : dto.getTitle();
        String checkedAuthorName= dto.getAuthorName() == null || dto.getAuthorName().isBlank()
                ? null : dto.getAuthorName();

        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1
                ,pageSize == null ? 10 : pageSize);

        var booksPage = bookRepository.findBooksByCategoryId(pageable,categoryId,checkedTitle,checkedAuthorName);

        List<BookListDto> books = booksPage.stream().map(book -> BookListDto.builder()
                .code(book.getCode())
                .title(book.getTitle())
                .category(book.getCategory().getName())
                .releaseDate(book.getReleaseDate())
                .isBorrowed(book.getIsBorrowed())
                .totalPage(book.getTotalPage())
                .author(book.getAuthor().getFullName())
                .summary(book.getSummary())
                .build()).toList();
        return new PageImpl<>(books,pageable,booksPage.getTotalElements());
    }

    @Override
    public Page<CategoryListDto> getAllBySearch(CategorySearchDto dto, Integer pageNumber, Integer pageSize) {
        String checkedName= dto.getName() == null || dto.getName().isBlank()
                ? null : dto.getName();

        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1
                ,pageSize == null ? 10 : pageSize);

        var categoriesPage = categoryRepository.findBySearch(pageable,checkedName);

        List<CategoryListDto> categories = categoriesPage.stream().map(category -> CategoryListDto.builder()
                .name(category.getName())
                .floor(category.getFloor())
                .bay(category.getBay())
                .isle(category.getIsle())
                .totalBooks(category.getBooks().size())
                .build()).toList();

        return new PageImpl<>(categories,pageable,categoriesPage.getTotalElements());
    }

    @Override
    public CategoryListDto getCategoryById(String categoryId) {
        var category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Id Category Not Found"));
        return CategoryListDto.builder()
                .name(category.getName())
                .floor(category.getFloor())
                .bay(category.getBay())
                .isle(category.getIsle())
                .totalBooks(category.getBooks().size())
                .build();
    }

    @Override
    public void insert(CategoryInsertDto dto) {
        if (categoryRepository.existsById(dto.getName())){
            throw new EntityExistsException("Id can't use");
        }
        var category = Category.builder()
                .name(dto.getName())
                .bay(dto.getBay())
                .floor(dto.getFloor())
                .isle(dto.getIsle())
                .build();
        categoryRepository.save(category);
    }

    @Override
    public void update(CategoryInsertDto dto, String categoryId) {
        var category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Id not found"));
        categoryRepository.delete(category);
        insert(dto);
    }

    @Override
    public void delete(String categoryId) {
        var category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Id not found"));
        categoryRepository.delete(category);
    }

    @Override
    public List<AuthorDropdownDto> getDropdownAuthor() {
        var authors = authorRepository.findAll();
        return authors.stream().map(author -> AuthorDropdownDto.builder()
                .id(author.getId())
                .name(author.getFullName())
                .build()).toList();
    }

    @Override
    public void insertBook(String categoryId, BookInsertDto dto) {
        var author = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        var category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        var book = bookRepository.findById(dto.getCode());

        if (book.isPresent()){
            throw new EntityExistsException("Id book already use");
        }
        var newBook = Book.builder()
                .code(dto.getCode())
                .title(dto.getTitle())
                .isBorrowed(false)
                .summary(dto.getSummary())
                .releaseDate(dto.getReleaseDate())
                .totalPage(dto.getTotalPage())
                .author(author)
                .category(category)
                .build();
        bookRepository.save(newBook);
    }

    @Override
    public BookItemDto getBookById(String bookId) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return BookItemDto.builder()
                .code(book.getCode())
                .title(book.getTitle())
                .releaseDate(book.getReleaseDate())
                .authorId(book.getAuthor().getId())
                .categoryId(book.getCategory().getName())
                .isBorrowed(book.getIsBorrowed())
                .totalPage(book.getTotalPage())
                .summary(book.getSummary())
                .build();
    }

    @Override
    public void updateBook(String bookId, BookUpdateDto dto) {
        var author = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("Author not found"));

        var book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        book.setTitle(dto.getTitle());
        book.setReleaseDate(dto.getReleaseDate());
        book.setAuthor(author);
        book.setSummary(dto.getSummary());
        book.setTotalPage(dto.getTotalPage());
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(String bookId) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        bookRepository.delete(book);
    }

    public BookItemDto bookItemDtoBuilder(Book book){
        return BookItemDto.builder()
                .code(book.getCode())
                .title(book.getTitle())
                .releaseDate(book.getReleaseDate())
                .authorId(book.getAuthor().getId())
                .categoryId(book.getCategory().getName())
                .isBorrowed(book.getIsBorrowed())
                .totalPage(book.getTotalPage())
                .summary(book.getSummary())
                .build();
    }

    @Override
    public BookItemDto insertBookApi(BookInsertDto dto) {
        var author = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        var category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        var book = bookRepository.findById(dto.getCode());

        if (book.isPresent()){
            throw new EntityExistsException("Id book already use");
        }
        var newBook = Book.builder()
                .code(dto.getCode())
                .title(dto.getTitle())
                .isBorrowed(false)
                .summary(dto.getSummary())
                .releaseDate(dto.getReleaseDate())
                .totalPage(dto.getTotalPage())
                .author(author)
                .category(category)
                .build();
        bookRepository.save(newBook);

        return bookItemDtoBuilder(newBook);
    }

    @Override
    public BookItemDto updateBookApi(BookUpdateDto dto, String id) {
        var author = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("Author not found"));

        var book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        book.setTitle(dto.getTitle());
        book.setReleaseDate(dto.getReleaseDate());
        book.setAuthor(author);
        book.setSummary(dto.getSummary());
        book.setTotalPage(dto.getTotalPage());
        bookRepository.save(book);

        return bookItemDtoBuilder(book);
    }

    @Override
    public BookItemDto deleteBookApi(String bookId) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        bookRepository.delete(book);
        return bookItemDtoBuilder(book);
    }

    @Override
    public Boolean isExistBook(String id) {
        var book = bookRepository.findById(id);
        return book.isPresent();
    }
}
