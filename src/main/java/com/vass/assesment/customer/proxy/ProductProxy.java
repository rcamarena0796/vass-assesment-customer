package com.vass.assesment.customer.proxy;


import com.vass.assesment.customer.model.external.Product;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ProductProxy.
 */
public interface ProductProxy {

  @GET("findByCustomerId/{customerId}")
  public Call<List<Product>> getProducts(@Path("customerId") Long customerId);

}