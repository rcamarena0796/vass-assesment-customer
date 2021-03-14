package com.vass.assesment.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Customer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "CUSTOMER")
@Builder(toBuilder = true)
public class Customer {

  @Id
  @NotNull(message = "id field is mandatory")
  private Long id;

  @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "Invalid numDoc!")
  @NotBlank(message = "name field is mandatory")
  private String numDoc;

  @NotBlank(message = "docType field is mandatory")
  @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Invalid docType!")
  private String docType;

  @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Invalid fullName!")
  @NotBlank(message = "fullName field is mandatory")
  private String fullName;

  @JsonFormat(pattern = "dd/MM/yyyy")
  @NotNull(message = "birthday field is mandatory")
  private Date birthday;
}
