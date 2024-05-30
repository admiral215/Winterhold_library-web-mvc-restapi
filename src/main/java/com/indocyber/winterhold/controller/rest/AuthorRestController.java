package com.indocyber.winterhold.controller.rest;

import com.indocyber.winterhold.dtos.author.*;
import com.indocyber.winterhold.dtos.book.BookListDto;
import com.indocyber.winterhold.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorRestController {
    private final AuthorService service;

    public AuthorRestController(AuthorService authorService) {
        this.service = authorService;
    }

    @GetMapping("")
    public ResponseEntity<Page<AuthorListDto>> getAll (@RequestParam(defaultValue = "1") Integer pageNumber,
                                                       @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(service.getAll(pageNumber,pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorItemDto> getById (@PathVariable BigInteger id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("insert")
    public ResponseEntity<AuthorItemDto> insert (@Valid @RequestBody AuthorInsertDto dto){
        return ResponseEntity.ok(service.insertApi(dto));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<AuthorItemDto> updateById ( @Valid @RequestBody AuthorUpdateDto dto, @PathVariable BigInteger id){
        return ResponseEntity.ok(service.updateApi(dto,id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<AuthorItemDto> deleteById (@PathVariable BigInteger id){
        return ResponseEntity.ok(service.deleteByIdApi(id));
    }

    @GetMapping("books/{id}")
    public ResponseEntity<Page<BookListDto>> getBooksByIdAuthor(@PathVariable BigInteger id){
        return ResponseEntity.ok(service.getBooksById(id));
    }
}
