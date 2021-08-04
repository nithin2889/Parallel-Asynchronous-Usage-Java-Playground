package io.playground.thread;

import static io.playground.util.CommonUtil.stopWatch;
import static io.playground.util.LoggerUtil.log;

import io.playground.domain.Product;
import io.playground.domain.ProductInfo;
import io.playground.domain.Review;
import io.playground.service.ProductInfoService;
import io.playground.service.ReviewService;

public class ProductServiceUsingThread {
  private ProductInfoService productInfoService;
  private ReviewService reviewService;

  public ProductServiceUsingThread(
      ProductInfoService productInfoService, ReviewService reviewService) {
    this.productInfoService = productInfoService;
    this.reviewService = reviewService;
  }

  public static void main(String[] args) throws InterruptedException {

    ProductInfoService productInfoService = new ProductInfoService();
    ReviewService reviewService = new ReviewService();
    ProductServiceUsingThread productService =
        new ProductServiceUsingThread(productInfoService, reviewService);
    String productId = "ABC123";
    Product product = productService.retrieveProductDetails(productId);
    log("Product is " + product);
  }

  public Product retrieveProductDetails(String productId) throws InterruptedException {
    stopWatch.start();

    ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
    Thread productInfoThread = new Thread(productInfoRunnable);

    ProductReviewRunnable productReviewRunnable = new ProductReviewRunnable(productId);
    Thread productReviewThread = new Thread(productReviewRunnable);

    productInfoThread.start();
    productReviewThread.start();

    productInfoThread.join();
    productReviewThread.join();

    ProductInfo productInfo = productInfoRunnable.getProductInfo();
    Review review = productReviewRunnable.getReview();

    stopWatch.stop();
    log("Total Time Taken : " + stopWatch.getTime());
    return new Product(productId, productInfo, review);
  }

  private class ProductInfoRunnable implements Runnable {
    private ProductInfo productInfo;
    private String productId;

    public ProductInfoRunnable(String productId) {
      this.productId = productId;
    }

    public ProductInfo getProductInfo() {
      return productInfo;
    }

    @Override
    public void run() {
      productInfo = productInfoService.retrieveProductInfo(productId);
    }
  }

  private class ProductReviewRunnable implements Runnable {

    private Review review;
    private String productId;

    public ProductReviewRunnable(String productId) {
      this.productId = productId;
    }

    public Review getReview() {
      return review;
    }

    @Override
    public void run() {
      review = reviewService.retrieveReviews(productId);
    }
  }
}
