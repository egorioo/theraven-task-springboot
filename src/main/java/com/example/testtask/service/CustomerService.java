package com.example.testtask.service;

import com.example.testtask.entity.Customer;
import com.example.testtask.entity.dto.CustomerDto;
import com.example.testtask.repository.ICustomerRepository;
import com.example.testtask.service.interfaces.ICustomerService;
import com.example.testtask.utils.CustomerConverter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final CustomerConverter customerConverter;

    public CustomerService(ICustomerRepository customerRepository,
                           CustomerConverter customerConverter) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    @Transactional
    public CustomerDto createCustomer(Customer customer) {
        customerRepository.save(customer);
        return customerConverter.customerToPojo(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomer(Long id) {
        Optional<Customer> customerOptional =  customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            return customerConverter.customerToPojo(customerOptional.get());
        } else {
            throw new NoSuchElementException("Customer with this ID does not exist");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> getAllCustomers() {
        List<Customer> listOfCustomers = customerRepository.findAll();
        if (!listOfCustomers.isEmpty()) {
            return listOfCustomers.stream().map(customerConverter::customerToPojo).collect(Collectors.toList());
        } else {
            throw new EmptyResultDataAccessException("no customer found",1);
        }
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(Customer customer, Long id) {
        Optional<Customer> customerToUpdateOptional = customerRepository.findById(id);
        if (customerToUpdateOptional.isPresent()) {
            Customer target = customerToUpdateOptional.get();
            //target.setId(customer.getId());
            target.setFullName(customer.getFullName());
            target.setPhone(customer.getPhone());
            customerRepository.save(target);
            return customerConverter.customerToPojo(target);
        } else {
            throw new NoSuchElementException("Customer with this ID does not exist");
        }
    }

    @Override
    @Transactional
    public String deleteCustomer(Long id) {
        Optional<Customer> customerToDeleteOptional = customerRepository.findById(id);
        if (customerToDeleteOptional.isPresent()) {
            Customer target = customerToDeleteOptional.get();
            target.setActive(false);
            customerRepository.save(target);
            return "Customer with ID: " + id + " successfully deleted";
        } else {
            throw new NoSuchElementException("Customer with this ID does not exist");
        }
    }
}
