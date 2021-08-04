package io.playground.service;

import static io.playground.util.CommonUtil.stopWatch;
import static io.playground.util.LoggerUtil.log;

import io.playground.domain.Product;
import io.playground.domain.ProductInfo;
import io.playground.domain.Review;

public class ProductService {
  private ProductInfoService productInfoService;
  private ReviewService reviewService;

  public ProductService(ProductInfoService productInfoService, ReviewService reviewService) {
    this.productInfoService = productInfoService;
    this.reviewService = reviewService;
  }

  public static void main(String[] args) {

    ProductInfoService productInfoService = new ProductInfoService();
    ReviewService reviewService = new ReviewService();
    ProductService productService = new ProductService(productInfoService, reviewService);
    String productId = "ABC123";
    Product product = productService.retrieveProductDetails(productId);
    log("Product is " + product);
  }

  public Product retrieveProductDetails(String productId) {
    stopWatch.start();

    ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
    Review review = reviewService.retrieveReviews(productId); // blocking call

    stopWatch.stop();
    log("Total Time Taken : " + stopWatch.getTime());
    return new Product(productId, productInfo, review);
  }
}
