package com.indocyber.winterhold.controller.rest;

import com.indocyber.winterhold.dtos.author.AuthorInsertDto;
import com.indocyber.winterhold.dtos.author.AuthorItemDto;
import com.indocyber.winterhold.dtos.author.AuthorListDto;
import com.indocyber.winterhold.dtos.author.AuthorUpdateDto;
import com.indocyber.winterhold.dtos.book.BookItemDto;
import com.indocyber.winterhold.dtos.book.BookListDto;
import com.indocyber.winterhold.dtos.category.CategoryInsertDto;
import com.indocyber.winterhold.dtos.category.CategoryItemDto;
import com.indocyber.winterhold.dtos.category.CategoryListDto;
import com.indocyber.winterhold.service.BookService;
import com.indocyber.winterhold.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryRestController {
    private final CategoryService service;

    public CategoryRestController(CategoryService service) {
        this.service = service;
    }


    @GetMapping("")
    public ResponseEntity<Page<CategoryListDto>> getAll (@RequestParam(defaultValue = "1") Integer pageNumber,
                                                         @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(service.getAll(pageNumber,pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryItemDto> getById (@PathVariable String id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("insert")
    public ResponseEntity<CategoryItemDto> insert (@Valid @RequestBody CategoryInsertDto dto){
        return ResponseEntity.ok(service.insertApi(dto));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<CategoryItemDto> updateById (@Valid @RequestBody CategoryInsertDto dto, @PathVariable String id){
        return ResponseEntity.ok(service.updateApi(dto,id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<CategoryItemDto> deleteById (@PathVariable String id){
        return ResponseEntity.ok(service.deleteByIdApi(id));
    }

    @GetMapping("books/{id}")
    public ResponseEntity<Page<BookListDto>> getBooksByIdCategory(@PathVariable String id){
        return ResponseEntity.ok(service.getBooksById(id));
    }
}
