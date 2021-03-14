package com.vass.assesment.customer.model.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Product.
 */
@Data
@AllArgsConstructor
@Document(collection = "PRODUCT")
@Builder(toBuilder = true)
public class Product {

  private Long id;
  private String name;
  private String technology;
  private Long customerId;

}
