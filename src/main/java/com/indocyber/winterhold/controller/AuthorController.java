package com.indocyber.winterhold.controller;

import com.indocyber.winterhold.dtos.author.AuthorInsertDto;
import com.indocyber.winterhold.dtos.author.AuthorSearchDto;
import com.indocyber.winterhold.dtos.author.AuthorUpdateDto;
import com.indocyber.winterhold.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService authorService) {
        this.service = authorService;
    }

    @GetMapping("")
    public ModelAndView index(@RequestParam(defaultValue = "1") Integer pageNumber,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              AuthorSearchDto dto){
        var mv = new ModelAndView("authors/index");
        mv.addObject("authors",service.getBySearch(dto,pageNumber,pageSize));
        mv.addObject("dto", dto);
        return mv;
    }

    @GetMapping("insert")
    public ModelAndView insert(){
        var mv = new ModelAndView("authors/insert");
        mv.addObject("dto",AuthorInsertDto.builder().build());
        return mv;
    }

    @PostMapping("insert")
    public String inserting(@Valid @ModelAttribute("dto") AuthorInsertDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println(dto);
            return "authors/insert";
        }
        service.insert(dto);
        return "redirect:/authors";
    }

    @GetMapping("update/{id}")
    public ModelAndView update(@PathVariable BigInteger id){
        var mv = new ModelAndView("authors/update");
        mv.addObject("author", service.getById(id));
        return mv;
    }

    @PostMapping("update/{id}")
    public String updating(@PathVariable BigInteger id, AuthorUpdateDto dto){
        service.update(id,dto);
        return "redirect:/authors";
    }

    @GetMapping("books/{id}")
    public ModelAndView books(@PathVariable BigInteger id){
        var mv = new ModelAndView("authors/books");
        mv.addObject("author", service.getById(id));
        mv.addObject("books",service.getBooksById(id));
        return mv;
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable BigInteger id){
        service.deleteById(id);
        return "redirect:/authors";
    }
}
