package com.indocyber.winterhold.controller;

import com.indocyber.winterhold.dtos.customer.CustomerSearchDto;
import com.indocyber.winterhold.dtos.loan.LoanInsertDto;
import com.indocyber.winterhold.dtos.loan.LoanSearchDto;
import com.indocyber.winterhold.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;

@Controller
@RequestMapping("/loans")
public class LoanController {
    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @GetMapping("")
    public ModelAndView index(LoanSearchDto dto,
                              @RequestParam(defaultValue = "1") Integer pageNumber,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        var mv = new ModelAndView("loans/index");
        mv.addObject("loans",service.getAllBySearch(dto,pageNumber,pageSize));
        mv.addObject("dto", dto);
        return mv;
    }

    @GetMapping("insert")
    public ModelAndView insert(){
        var mv = new ModelAndView("loans/insert");
        mv.addObject("dto",LoanInsertDto.builder().build());
        mv.addObject("customers",service.getDropdownCustomer());
        mv.addObject("books", service.getDropdownBook());
        return mv;
    }

    @PostMapping("insert")
    public String insert(@Valid @ModelAttribute("dto") LoanInsertDto dto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("dto",dto);
            model.addAttribute("customers",service.getDropdownCustomer());
            model.addAttribute("books", service.getDropdownBook());
            return "loans/insert";
        }
        service.insert(dto);
        return "redirect:/loans";
    }

    @GetMapping("update/{loanId}")
    public ModelAndView update(@PathVariable BigInteger loanId){
        var mv = new ModelAndView("loans/update");
        mv.addObject("customers",service.getDropdownCustomer());
        mv.addObject("books", service.getDropdownBook());
        mv.addObject("loan",service.getById(loanId));
        return mv;
    }

    @PostMapping("update/{loanId}")
    public String update(LoanInsertDto dto, @PathVariable BigInteger loanId){
        service.update(dto,loanId);
        return "redirect:/loans";
    }

    @PostMapping("delete/{loanId}")
    public String delete( @PathVariable BigInteger loanId){
        service.delete(loanId);
        return "redirect:/loans";
    }

    @PostMapping("return/{loanId}")
    public String returnBook ( @PathVariable BigInteger loanId){
        service.returnBook(loanId);
        return "redirect:/loans";
    }

    @GetMapping("details/{loanId}")
    public ModelAndView details (@PathVariable BigInteger loanId){
        var mv = new ModelAndView("loans/details");
        mv.addObject("customer", service.getCustomer(loanId));
        mv.addObject("book", service.getBook(loanId));
        return mv;
    }
}
