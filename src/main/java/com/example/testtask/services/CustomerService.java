package com.example.testtask.services;

import com.example.testtask.entities.Customer;
import com.example.testtask.entities.dto.CustomerDto;
import com.example.testtask.repositories.ICustomerRepository;
import com.example.testtask.services.interfaces.ICustomerService;
import com.example.testtask.utils.CustomerConverter;
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
        customer.setCreated(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        customerRepository.save(customer);
        return customerConverter.customerToPojo(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> getAllCustomers() {
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
    public CustomerDto getCustomer(Long id) {
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
    public CustomerDto updateCustomer(Customer customer, Long id) {
        Optional<Customer> customerToUpdateOptional = customerRepository.findById(id);
        if (customerToUpdateOptional.isPresent()) {
            Customer target = customerToUpdateOptional.get();
            /*In the requirements, in the body of the request, there is an ID that needs to be changed,
            but I'm not sure that changing the primary key is a good practice*/
            //target.setId(customer.getId());
            target.setFullName(customer.getFullName());
            target.setPhone(customer.getPhone());
            target.setUpdated(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
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
