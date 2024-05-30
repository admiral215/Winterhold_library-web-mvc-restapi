package com.indocyber.winterhold.controller.rest;

import com.indocyber.winterhold.dtos.customer.CustomerInsertDto;
import com.indocyber.winterhold.dtos.customer.CustomerItemDto;
import com.indocyber.winterhold.dtos.customer.CustomerListDto;
import com.indocyber.winterhold.dtos.customer.CustomerUpdateDto;
import com.indocyber.winterhold.service.CustomerService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerRestController {
    private final CustomerService service;

    public CustomerRestController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<Page<CustomerListDto>> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                        @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(service.getAllApi(pageNumber,pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerItemDto> getCustomerById(@PathVariable String id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("insert")
    public ResponseEntity<CustomerItemDto> insert(@Valid @RequestBody CustomerInsertDto dto){
        return ResponseEntity.ok(service.insertApi(dto));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<CustomerItemDto> update( @RequestBody CustomerUpdateDto dto, @PathVariable String id){
        return ResponseEntity.ok(service.updateApi(dto, id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<CustomerItemDto> delete(@PathVariable String id){
        return ResponseEntity.ok(service.deleteApi(id));
    }
}
