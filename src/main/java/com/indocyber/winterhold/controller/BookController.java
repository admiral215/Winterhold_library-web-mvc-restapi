package com.indocyber.winterhold.controller;

import com.indocyber.winterhold.dtos.book.BookInsertDto;
import com.indocyber.winterhold.dtos.book.BookSearchDto;
import com.indocyber.winterhold.dtos.book.BookUpdateDto;
import com.indocyber.winterhold.dtos.category.CategoryInsertDto;
import com.indocyber.winterhold.dtos.category.CategorySearchDto;
import com.indocyber.winterhold.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("")
    public ModelAndView index(CategorySearchDto dto,
                              @RequestParam(defaultValue = "1") Integer pageNumber,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        var mv = new ModelAndView("books/index");
        mv.addObject("categories", service.getAllBySearch(dto,pageNumber,pageSize));
        mv.addObject("dto",dto);
        return mv;
    }

    @GetMapping("insert")
    public ModelAndView insert(){
        var mv = new ModelAndView("books/insert");
        mv.addObject("dto",CategoryInsertDto.builder().build());
        return mv;
    }

    @PostMapping("insert")
    public String inserting(@Valid @ModelAttribute("dto") CategoryInsertDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/insert";
        }
        service.insert(dto);
        return "redirect:/books";
    }

    @GetMapping("update/{categoryId}")
    public ModelAndView update(@PathVariable String categoryId){
        var mv = new ModelAndView("books/update");
        mv.addObject("category", service.getCategoryById(categoryId));
        return mv;
    }

    @PostMapping("update/{categoryId}")
    public String updating(CategoryInsertDto dto, @PathVariable String categoryId){
        service.update(dto,categoryId);
        return "redirect:/books";
    }

    @PostMapping("delete/{categoryId}")
    public String delete(@PathVariable String categoryId){
        service.delete(categoryId);
        return "redirect:/books";
    }

//    ----------------------------DETAILS----------------------------------

    @GetMapping("details/{categoryId}")
    public ModelAndView details (@PathVariable String categoryId,
                               BookSearchDto dto,
                               @RequestParam(defaultValue = "1") Integer pageNumber,
                               @RequestParam(defaultValue = "10") Integer pageSize){
        var mv = new ModelAndView("books/details/details");
        mv.addObject("dto",dto);
        mv.addObject("category", service.getCategoryById(categoryId));
        mv.addObject("books",service.getAllByCategoryId(dto, categoryId,pageNumber, pageSize));
        return mv;
    }

    @GetMapping("details/{categoryId}/insert")
    public ModelAndView insert (@PathVariable String categoryId){
        var mv = new ModelAndView("books/details/insert");
        mv.addObject("dto",BookInsertDto.builder().build());
        mv.addObject("category",service.getCategoryById(categoryId));
        mv.addObject("authors",service.getDropdownAuthor());
        return mv;
    }

    @PostMapping("details/{categoryId}/insert")
    public String inserting(@PathVariable String categoryId, @Valid @ModelAttribute("dto") BookInsertDto dto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("dto", dto);
            model.addAttribute("category",service.getCategoryById(categoryId));
            model.addAttribute("authors",service.getDropdownAuthor());
            return "/books/details/insert";
        }
        service.insertBook(categoryId,dto);
        return "redirect:/books/details/{categoryId}";
    }

    @GetMapping("details/{categoryId}/update/{bookId}")
    public ModelAndView updateBook (@PathVariable String categoryId, @PathVariable String bookId){
        var mv = new ModelAndView("books/details/update");
        mv.addObject("book",service.getBookById(bookId));
        mv.addObject("category",service.getCategoryById(categoryId));
        mv.addObject("authors",service.getDropdownAuthor());
        return mv;
    }

    @PostMapping("details/{categoryId}/update/{bookId}")
    public String updating(@PathVariable String categoryId, @PathVariable String bookId, BookUpdateDto dto){
        service.updateBook(bookId,dto);
        return "redirect:/books/details/{categoryId}";
    }

    @PostMapping("details/{categoryId}/delete/{bookId}")
    public String deleteBook(@PathVariable String bookId, @PathVariable String categoryId){
        service.deleteBook(bookId);
        return "redirect:/books/details/{categoryId}";
    }

}
