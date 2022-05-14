package com.example.testtask.services.interfaces;

import com.example.testtask.entities.dto.request.CustomerRequestDto;
import com.example.testtask.entities.dto.response.CustomerResponseDto;
import com.example.testtask.entities.dto.request.CustomerPatchRequestDto;
import com.example.testtask.utils.Response;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerRequestDto customer);

    CustomerResponseDto getCustomer(Long id);

    List<CustomerResponseDto> getAllCustomers();

    CustomerResponseDto updateCustomer(CustomerRequestDto customer, Long id);

    Response deleteCustomer(Long id);

    CustomerResponseDto patchUpdateCustomer(CustomerPatchRequestDto customer, Long id);
}
