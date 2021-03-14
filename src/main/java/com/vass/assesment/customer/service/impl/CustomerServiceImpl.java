package com.vass.assesment.customer.service.impl;


import com.vass.assesment.customer.dao.CustomerRepository;
import com.vass.assesment.customer.model.Customer;
import com.vass.assesment.customer.model.dto.CustomerDto;
import com.vass.assesment.customer.model.external.Product;
import com.vass.assesment.customer.proxy.ProductProxy;
import com.vass.assesment.customer.service.CustomerService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ProductServiceImpl.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

  private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

  private CustomerRepository customerRepository;

  private ProductProxy productProxy;

  /**
   * CustomerServiceImpl.
   */
  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8001/api/product/")
        .addConverterFactory(
            GsonConverterFactory.create()).client(httpClient.build()).build();
    productProxy = retrofit.create(ProductProxy.class);
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
  public Optional<CustomerDto> findCustomer(Long customerId) {
    Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
    if (!optionalCustomer.isPresent()) {
      return Optional.empty();
    } else {
      CustomerDto customerDto = mapCustomer(optionalCustomer.get(),
          findProducts(optionalCustomer.get().getId()));
      return Optional.of(customerDto);
    }
  }

  @Override
  public List<CustomerDto> findAll() {
    return customerRepository.findAll().stream()
        .map(customer -> mapCustomer(customer, findProducts(customer.getId())))
        .collect(Collectors.toList());
  }

  private List<Product> findProducts(Long customerId) {
    Call<List<Product>> callSync = productProxy.getProducts(customerId);

    try {
      Response<List<Product>> response = callSync.execute();
      List<Product> products = response.body();
      return products;
    } catch (Exception ex) {
      logger.info("error: " + ex.getMessage());
      return new ArrayList<>();
    }
  }

  private CustomerDto mapCustomer(Customer customer, List<Product> products) {
    return CustomerDto.builder().birthday(customer.getBirthday()).docType(customer.getDocType())
        .id(customer.getId()).numDoc(customer.getNumDoc()).fullName(customer.getFullName())
        .products(products).build();
  }
}
