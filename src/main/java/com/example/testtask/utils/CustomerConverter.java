package com.example.testtask.utils;

import com.example.testtask.entity.Customer;
import com.example.testtask.entity.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {
    public CustomerDto customerToPojo(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setEmail(customer.getEmail());
        customerDto.setFullName(customer.getFullName());
        customerDto.setPhone(customer.getPhone());
        return customerDto;
    }
}
