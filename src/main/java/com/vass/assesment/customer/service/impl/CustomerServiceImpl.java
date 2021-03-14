package com.vass.assesment.customer.service.impl;


import com.vass.assesment.customer.dao.CustomerRepository;
import com.vass.assesment.customer.model.Customer;
import com.vass.assesment.customer.service.CustomerService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * ProductServiceImpl.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

  private CustomerRepository customerRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }


  @Override
  public Customer saveOrUpdateCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public void deleteCustomer(Customer customer) {
    customerRepository.delete(customer);

  }

  @Override
  public Optional<Customer> findCustomer(Long customerId) {
    return customerRepository.findById(customerId);
  }

  @Override
  public List<Customer> findAll() {
    return customerRepository.findAll();
  }
}
