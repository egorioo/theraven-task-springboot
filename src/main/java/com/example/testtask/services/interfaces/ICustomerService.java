package com.example.testtask.services.interfaces;

import com.example.testtask.entities.Customer;
import com.example.testtask.entities.dto.CustomerDto;

import java.util.List;

public interface ICustomerService {
    CustomerDto createCustomer(Customer customer);

    CustomerDto getCustomer(Long id);

    List<CustomerDto> getAllCustomers();

    CustomerDto updateCustomer(Customer customer, Long id);

    String deleteCustomer(Long id);
}