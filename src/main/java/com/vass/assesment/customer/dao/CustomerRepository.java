package com.vass.assesment.customer.dao;


import com.vass.assesment.customer.model.Customer;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * CustomerRepository.
 */
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

  Optional<Customer> findById(Long id);

}