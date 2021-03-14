package com.vass.assesment.customer.expose.web;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vass.assesment.customer.model.Customer;
import com.vass.assesment.customer.model.dto.CustomerDto;
import com.vass.assesment.customer.service.CustomerService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  protected CustomerService customerService;

  private static Customer customer;

  private static CustomerDto customerDto;

  private static List<CustomerDto> customerDtoList;

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
      customerDto = CustomerDto.builder().birthday(customer.getBirthday())
          .docType(customer.getDocType())
          .id(customer.getId()).numDoc(customer.getNumDoc()).fullName(customer.getFullName())
          .products(new ArrayList<>()).build();

    } catch (Exception e) {
    }
  }

  @Test
  public void findAllShouldReturnOk() throws Exception {
    doReturn(customerDtoList).when(customerService).findAll();
    this.mockMvc.perform(get("/api/customer/findAll"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(mapToJson(customerDtoList))));
  }

  @Test
  public void findShouldReturnOk() throws Exception {
    Long customerId = 1L;
    doReturn(Optional.of(customerDto)).when(customerService).findCustomer(customerId);
    this.mockMvc.perform(get("/api/customer/"+customerId))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(mapToJson(customerDto))));
  }

  @Test
  public void findShouldReturnNotFound() throws Exception {
    Long customerId = 1L;
    doReturn(Optional.empty()).when(customerService).findCustomer(customerId);
    this.mockMvc.perform(get("/api/customer/"+customerId))
        .andDo(print())
        .andExpect(status().isNotFound());
  }




  @Test
  public void createCustomerShouldReturnOk() throws Exception {
    when(customerService.saveOrUpdateCustomer(Mockito.any())).thenReturn(customer);
    this.mockMvc.perform(post("/api/customer").accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(mapToJson(customer))).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString(mapToJson(customer))));
  }


  @Test
  public void createCustomerShouldReturnError() throws Exception {
    Customer badCust = Customer.builder().birthday(new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1998"))
        .docType("aaa")
        .id(1L).numDoc("asdad").fullName("Ruben Camarena")
        .build();
    when(customerService.saveOrUpdateCustomer(Mockito.any())).thenReturn(badCust);
    this.mockMvc.perform(post("/api/customer").accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(mapToJson(badCust))).andDo(print()).andExpect(status().isBadRequest());
  }


  @Test
  public void testDelete() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders
        .delete("/api/customer").accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(mapToJson(customer))).andDo(print()).andExpect(status().isOk())
        .andExpect(status().isOk());
  }


  private String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

}