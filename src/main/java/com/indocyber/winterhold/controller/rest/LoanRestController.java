package com.indocyber.winterhold.controller.rest;

import com.indocyber.winterhold.dtos.loan.LoanInsertDto;
import com.indocyber.winterhold.dtos.loan.LoanItemDto;
import com.indocyber.winterhold.dtos.loan.LoanListDto;
import com.indocyber.winterhold.dtos.loan.LoanSearchDto;
import com.indocyber.winterhold.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;

@RestController
@RequestMapping("api/v1/loans")
public class LoanRestController {
    private final LoanService service;

    public LoanRestController(LoanService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<Page<LoanListDto>> index(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                   @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(service.getAllApi(pageNumber,pageSize));
    }

    @PostMapping("insert")
    public ResponseEntity<LoanItemDto> insert(@Valid @RequestBody LoanInsertDto dto){
        return ResponseEntity.ok(service.insertApi(dto));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<LoanItemDto> update( @RequestBody LoanInsertDto dto , @PathVariable
    BigInteger id){
        return ResponseEntity.ok(service.updateApi(dto, id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<LoanItemDto> delete( @PathVariable
    BigInteger id){
        return ResponseEntity.ok(service.deleteApi(id));
    }
}
