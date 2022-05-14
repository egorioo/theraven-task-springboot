package com.example.testtask.utils;

import com.example.testtask.entities.Customer;
import com.example.testtask.entities.dto.response.CustomerResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {
    public CustomerResponseDto customerToPojo(Customer customer) {
        CustomerResponseDto customerDto = new CustomerResponseDto();
        customerDto.setId(customer.getId());
        customerDto.setEmail(customer.getEmail());
        customerDto.setFullName(customer.getFullName());
        customerDto.setPhone(customer.getPhone());
        return customerDto;
    }
}
