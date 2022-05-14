package com.example.testtask.controllers;

import com.example.testtask.entities.dto.request.CustomerRequestDto;
import com.example.testtask.entities.dto.response.CustomerResponseDto;
import com.example.testtask.entities.dto.request.CustomerPatchRequestDto;
import com.example.testtask.services.interfaces.CustomerService;
import com.example.testtask.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@Validated @RequestBody CustomerRequestDto customer) {
        CustomerResponseDto result = customerService.createCustomer(customer);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<CustomerResponseDto> list = customerService.getAllCustomers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable Long id) {
        CustomerResponseDto result = customerService.getCustomer(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@Validated @RequestBody CustomerRequestDto customer, @PathVariable Long id) {
        CustomerResponseDto result = customerService.updateCustomer(customer, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> patchUpdateCustomer(@Validated @RequestBody CustomerPatchRequestDto customer, @PathVariable Long id) {
        CustomerResponseDto customerDto=  customerService.patchUpdateCustomer(customer,id);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }
}
