package com.example.testtask.repositories;

import com.example.testtask.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAll();

    @Query("from Customer c where c.id = ?1 and c.isActive = true ")
    Optional<Customer> getCustomerByIdAndActive(Long id);

    @Query("from Customer c where c.isActive = true")
    List<Customer> findAllByActive();
}
