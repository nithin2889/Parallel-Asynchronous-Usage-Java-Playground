package io.playground.service;

import static io.playground.util.CommonUtil.delay;

import io.playground.domain.ProductInfo;
import io.playground.domain.ProductOption;
import java.util.Arrays;
import java.util.List;

public class ProductInfoService {

  public ProductInfo retrieveProductInfo(String productId) {
    delay(1000);
    List<ProductOption> productOptions =
        Arrays.asList(
            new ProductOption(1, "64GB", "Black", 699.99),
            new ProductOption(2, "128GB", "Black", 749.99));
    return ProductInfo.builder().productId(productId).productOptions(productOptions).build();
  }
}
