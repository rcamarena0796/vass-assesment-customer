package com.vass.assesment.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.vass.assesment.customer.dao.CustomerRepository;
import com.vass.assesment.customer.model.Customer;
import com.vass.assesment.customer.model.dto.CustomerDto;
import com.vass.assesment.customer.proxy.ProductProxy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import retrofit2.Call;


@SpringBootTest
public class CustomerServiceTest {

  @MockBean
  private CustomerRepository customerRepository;

  @MockBean
  private ProductProxy productProxy;

  @Autowired
  private CustomerService customerService;

  @Mock
  private static Customer customer;

  @Mock
  private static List<CustomerDto> customerDtoList;

  @Mock
  private static List<Customer> customerList;


  @BeforeAll
  public static void setUp() {
    try {
      customerDtoList = new ArrayList<>();
      customerList = new ArrayList<>();
      customerDtoList.add(
          CustomerDto.builder().birthday(new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1998"))
              .docType("aaa")
              .id(1L).numDoc("123123").fullName("Ruben Camarena")
              .build());
      customerDtoList.add(
          CustomerDto.builder().birthday(new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1998"))
              .docType("aaa")
              .id(2L).numDoc("123123").fullName("Ruben Camarena")
              .build());
      customerList.add(
          Customer.builder().birthday(new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1998"))
              .docType("aaa")
              .id(1L).numDoc("123123").fullName("Ruben Camarena")
              .build());
      customerList.add(
          Customer.builder().birthday(new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1998"))
              .docType("aaa")
              .id(2L).numDoc("123123").fullName("Ruben Camarena")
              .build());

      customer = Customer.builder().birthday(new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1998"))
          .docType("aaa")
          .id(1L).numDoc("123123").fullName("Ruben Camarena")
          .build();

    } catch (Exception e) {
    }
  }

  @Test
  public void createCustomerShouldReturnOk() {
    when(customerRepository.save(Mockito.any())).thenReturn(customer);
    Customer response = customerService.saveOrUpdateCustomer(customer);

    assertEquals(response.getId(), customer.getId());
  }

  @Test
  public void getAllShouldReturnOk() {
    when(customerRepository.findAll()).thenReturn(customerList);
    List<CustomerDto> customers = customerService.findAll();
    when(productProxy.getProducts(Mockito.any())).thenReturn(mock(Call.class));

    assertEquals(customers.size(), customerDtoList.size());
  }

  @Test
  public void findByIdShouldReturnOk() {
    when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
    when(productProxy.getProducts(Mockito.any())).thenReturn(mock(Call.class));

    Optional<CustomerDto> response = customerService.findCustomer(1L);

    assertEquals(response.get().getId(), customer.getId());
  }

  @Test
  public void findByIdShouldReturnEmpty() {
    when(customerRepository.findById(1L)).thenReturn(Optional.empty());

    Optional<CustomerDto> response = customerService.findCustomer(1L);

    assertTrue(!response.isPresent());
  }


  @Test
  public void testDelete() {
    customerService.deleteCustomer(customer);
    Mockito.verify(customerRepository, times(1)).delete(customer);
  }
}