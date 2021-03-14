package com.vass.assesment.customer.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.vass.assesment.customer.model.external.Product;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Customer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "CUSTOMER")
@Builder(toBuilder = true)
public class CustomerDto {

  private Long id;
  private String numDoc;
  private String docType;
  private String fullName;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date birthday;
  private List<Product> products;
}