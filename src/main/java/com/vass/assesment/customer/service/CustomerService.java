package com.vass.assesment.customer.service;

import com.vass.assesment.customer.model.Customer;
import com.vass.assesment.customer.model.dto.CustomerDto;
import java.util.List;
import java.util.Optional;

/**
 * CustomerService.
 */
public interface CustomerService {

  public Customer saveOrUpdateCustomer(Customer customer);

  public void deleteCustomer(Customer customer);

  public Optional<CustomerDto> findCustomer(Long customerId);

  public List<CustomerDto> findAll();

}