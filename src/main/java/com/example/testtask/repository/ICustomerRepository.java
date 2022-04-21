package com.example.testtask.repository;

import com.example.testtask.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer,Long> {
    List<Customer> findAll();
}
