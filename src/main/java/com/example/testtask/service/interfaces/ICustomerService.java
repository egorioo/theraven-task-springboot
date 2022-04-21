package com.example.testtask.service.interfaces;

import com.example.testtask.entity.Customer;
import com.example.testtask.entity.dto.CustomerDto;

import java.util.List;

public interface ICustomerService {
    CustomerDto createCustomer(Customer customer);
    CustomerDto getCustomer(Long id);
    List<CustomerDto> getAllCustomers();
    CustomerDto updateCustomer(Customer customer, Long id);
    String deleteCustomer(Long id);
}