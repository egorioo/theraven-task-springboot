package com.example.testtask.controller;

import com.example.testtask.entity.Customer;
import com.example.testtask.entity.dto.CustomerDto;
import com.example.testtask.service.interfaces.ICustomerService;
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

    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Validated @RequestBody Customer customer) {
        CustomerDto result = customerService.createCustomer(customer);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> list = customerService.getAllCustomers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {
        CustomerDto result = customerService.getCustomer(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@Validated @RequestBody Customer customer, @PathVariable Long id) {
        CustomerDto result = customerService.updateCustomer(customer, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }

    //Exceptions

    @ExceptionHandler
    public ResponseEntity<String> emailConflictException(DataIntegrityViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getClass().getSimpleName() + ": Client with such email already exists");
    }

    @ExceptionHandler
    public ResponseEntity<String> clientNotExistException(NoSuchElementException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getClass().getSimpleName() + ": No such customer exists");
    }

    @ExceptionHandler
    public ResponseEntity<String> emptyResultException(EmptyResultDataAccessException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getClass().getSimpleName() + ": No one customer found");
    }

    @ExceptionHandler
    public ResponseEntity<Object> notValidDataException(MethodArgumentNotValidException exception) {

        List<String> details = new ArrayList<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getClass().getSimpleName() + ": Not valid\n" + details);
    }
}
