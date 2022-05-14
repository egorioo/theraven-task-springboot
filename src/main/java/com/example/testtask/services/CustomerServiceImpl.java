package com.example.testtask.services;

import com.example.testtask.entities.Customer;
import com.example.testtask.entities.dto.request.CustomerRequestDto;
import com.example.testtask.entities.dto.response.CustomerResponseDto;
import com.example.testtask.entities.dto.request.CustomerPatchRequestDto;
import com.example.testtask.repositories.CustomerRepository;
import com.example.testtask.utils.CustomerConverter;
import com.example.testtask.utils.Response;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements com.example.testtask.services.interfaces.CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerConverter customerConverter) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    @Transactional
    public CustomerResponseDto createCustomer(CustomerRequestDto customer) {
        Customer result = new Customer();
        result.setEmail(customer.getEmail());
        result.setPhone(customer.getPhone());
        result.setFullName(customer.getFullName());
        result.setCreated(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        customerRepository.save(result);
        return customerConverter.customerToPojo(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getAllCustomers() {
        /*it was not specified whether to return clients that were deleted, where isActive = false. so there are two options*/

        //all customers, even deleted ones
        //List<Customer> listOfCustomers = customerRepository.findAll();

        //only active customers
        List<Customer> listOfCustomers = customerRepository.findAllByActive();
        if (!listOfCustomers.isEmpty()) {
            return listOfCustomers.stream().map(customerConverter::customerToPojo).collect(Collectors.toList());
        } else {
            throw new EmptyResultDataAccessException("no customer found", 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDto getCustomer(Long id) {
        //choice among all customers
        //Optional<Customer> customerOptional =  customerRepository.findById(id);

        //choice only among active clients
        Optional<Customer> customerOptional = customerRepository.getCustomerByIdAndActive(id);
        if (customerOptional.isPresent()) {
            return customerConverter.customerToPojo(customerOptional.get());
        } else {
            throw new NoSuchElementException("Customer with this ID does not exist");
        }
    }

    @Override
    @Transactional
    public CustomerResponseDto updateCustomer(CustomerRequestDto customer, Long id) {
        Optional<Customer> customerToUpdateOptional = customerRepository.findById(id);
        if (customerToUpdateOptional.isPresent()) {
            Customer target = customerToUpdateOptional.get();
            /*In the requirements, in the body of the request, there is an ID that needs to be changed,
            but I'm not sure that changing the primary key is a good practice*/
            //target.setId(customer.getId());
            target.setFullName(customer.getFullName());
            target.setPhone(customer.getPhone());
            target.setUpdated(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            return customerConverter.customerToPojo(target);
        } else {
            throw new NoSuchElementException("Customer with this ID does not exist");
        }
    }

    @Override
    @Transactional
    public Response deleteCustomer(Long id) {
        Optional<Customer> customerToDeleteOptional = customerRepository.findById(id);
        if (customerToDeleteOptional.isPresent()) {
            Customer target = customerToDeleteOptional.get();
            target.setActive(false);
            target.setUpdated(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            return new Response("Customer with ID: " + id + " successfully deleted");
        } else {
            throw new NoSuchElementException("Customer with this ID does not exist");
        }
    }

    @Transactional
    public CustomerResponseDto patchUpdateCustomer(CustomerPatchRequestDto customer, Long id) {
        Optional<Customer> customerToUpdateOptional = customerRepository.findById(id);

        if (customerToUpdateOptional.isPresent()) {
            Customer target = customerToUpdateOptional.get();
            if (customer.getFullName() != null) {
                target.setFullName(customer.getFullName());
            }
            if (customer.getPhone() != null) {
                target.setPhone(customer.getPhone());
            }
            return customerConverter.customerToPojo(target);
        } else {
            throw new NoSuchElementException("Customer with this ID does not exist");
        }
    }
}
