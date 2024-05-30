package com.indocyber.winterhold.controller.rest;

import com.indocyber.winterhold.dtos.book.BookInsertDto;
import com.indocyber.winterhold.dtos.book.BookItemDto;
import com.indocyber.winterhold.dtos.book.BookUpdateDto;
import com.indocyber.winterhold.dtos.book.checkCodeBooResponse;
import com.indocyber.winterhold.dtos.category.CategoryInsertDto;
import com.indocyber.winterhold.dtos.category.CategoryItemDto;
import com.indocyber.winterhold.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/books")
public class BookRestController {
    private final BookService service;

    public BookRestController(BookService bookService) {
        this.service = bookService;
    }

    @PostMapping("insert")
    public ResponseEntity<BookItemDto> insert (@Valid @RequestBody BookInsertDto dto){
        return ResponseEntity.ok(service.insertBookApi(dto));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<BookItemDto> update (@Valid @RequestBody BookUpdateDto dto, @PathVariable String id){
        return ResponseEntity.ok(service.updateBookApi(dto,id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<BookItemDto> delete (@PathVariable String id){
        return ResponseEntity.ok(service.deleteBookApi(id));
    }

    @GetMapping("check/{id}")
    public ResponseEntity<Boolean> isExistBook (@PathVariable String id){
        return ResponseEntity.ok(service.isExistBook(id));
    }
}
