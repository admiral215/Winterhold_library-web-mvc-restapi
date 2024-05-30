package com.indocyber.winterhold.controller;

import com.indocyber.winterhold.dtos.customer.CustomerInsertDto;
import com.indocyber.winterhold.dtos.customer.CustomerSearchDto;
import com.indocyber.winterhold.dtos.customer.CustomerUpdateDto;
import com.indocyber.winterhold.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("")
    public ModelAndView index(CustomerSearchDto dto,
                              @RequestParam(defaultValue = "1") Integer pageNumber,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        var mv = new ModelAndView("customers/index");
        mv.addObject("customers",service.getAllBySearch(dto,pageNumber,pageSize));
        mv.addObject("dto", dto);
        return mv;
    }


    @GetMapping("insert")
    public ModelAndView insert(){
        var mv = new ModelAndView("customers/insert");
        mv.addObject("dto", CustomerInsertDto.builder().build());
        return mv;
    }

    @PostMapping("insert")
    public String inserting(@Valid @ModelAttribute("dto") CustomerInsertDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "customers/insert";
        }
        service.insert(dto);
        return "redirect:/customers";
    }

    @GetMapping("details/{membershipNumber}")
    public ModelAndView details(@PathVariable String membershipNumber){
        var mv = new ModelAndView("customers/details");
        mv.addObject("customer",service.getById(membershipNumber));
        return mv;
    }

    @GetMapping("update/{membershipNumber}")
    public ModelAndView update(@PathVariable String membershipNumber){
        var mv = new ModelAndView("customers/update");
        mv.addObject("customer",service.getById(membershipNumber));
        return mv;
    }


    @PostMapping("update/{membershipNumber}")
    public String updating(@PathVariable String membershipNumber, CustomerUpdateDto dto){
        service.update(membershipNumber,dto);
        return "redirect:/customers";
    }

    @PostMapping("delete/{membershipNumber}")
    public String delete(@PathVariable String membershipNumber){
        service.delete(membershipNumber);
        return "redirect:/customers";
    }

    @PostMapping("extend/{membershipNumber}")
    public String extend(@PathVariable String membershipNumber){
        service.extend(membershipNumber);
        return "redirect:/customers";
    }
}
