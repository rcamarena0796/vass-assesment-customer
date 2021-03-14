package com.vass.assesment.customer.expose.web;


import com.vass.assesment.customer.model.Customer;
import com.vass.assesment.customer.model.dto.CustomerDto;
import com.vass.assesment.customer.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductController.
 */
@Api(tags = "Customer API", value = "CRUD operations for customers")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

  @Autowired
  private CustomerService service;

  @ApiOperation(value = "Endpoint used to create or update a product")
  @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Customer> createProduct(@Valid @RequestBody Customer customer) {
    return ResponseEntity.ok().body(service.saveOrUpdateCustomer(customer));
  }

  @ApiOperation(value = "Endpoint used to return all customers")
  @GetMapping("findAll")
  public ResponseEntity<List<CustomerDto>> findAll() {
    return ResponseEntity.ok().body(service.findAll());
  }

  /**
   * findById.
   */
  @ApiOperation(value = "Endpoint used to find a customer")
  @GetMapping("/{customerId}")
  public ResponseEntity<CustomerDto> findById(@PathVariable Long customerId) {
    Optional<CustomerDto> existingCustomer = service.findCustomer(customerId);
    if (!existingCustomer.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    try {
      return ResponseEntity
          .ok()
          .body(existingCustomer.get());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @ApiOperation(value = "Endpoint used to delete a customer")
  @DeleteMapping("")
  public void deleteProduct(@RequestBody Customer customer) {
    service.deleteCustomer(customer);
  }

  /**
   * handleValidationExceptions.
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  /**
   * handleNotReadableExceptions.
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public String handleNotReadableExceptions(
      HttpMessageNotReadableException ex) {
    return ex.getMessage();
  }

}