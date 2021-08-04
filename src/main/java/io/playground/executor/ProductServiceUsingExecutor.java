package io.playground.executor;

import static io.playground.util.CommonUtil.stopWatch;
import static io.playground.util.LoggerUtil.log;

import io.playground.domain.Product;
import io.playground.domain.ProductInfo;
import io.playground.domain.Review;
import io.playground.service.ProductInfoService;
import io.playground.service.ReviewService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProductServiceUsingExecutor {
  static ExecutorService executorService =
      Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  private ProductInfoService productInfoService;
  private ReviewService reviewService;

  public ProductServiceUsingExecutor(
      ProductInfoService productInfoService, ReviewService reviewService) {
    this.productInfoService = productInfoService;
    this.reviewService = reviewService;
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

    ProductInfoService productInfoService = new ProductInfoService();
    ReviewService reviewService = new ReviewService();
    ProductServiceUsingExecutor productService =
        new ProductServiceUsingExecutor(productInfoService, reviewService);
    String productId = "ABC123";
    Product product = productService.retrieveProductDetails(productId);
    log("Product is " + product);
  }

  public Product retrieveProductDetails(String productId)
      throws ExecutionException, InterruptedException, TimeoutException {
    stopWatch.start();

    Future<ProductInfo> productInfoFuture =
        executorService.submit(() -> productInfoService.retrieveProductInfo(productId));
    Future<Review> reviewFuture = executorService.submit(() -> reviewService.retrieveReviews(productId));

    // ProductInfo productInfo = productInfoFuture.get();
    ProductInfo productInfo = productInfoFuture.get(2, TimeUnit.SECONDS);
    Review review = reviewFuture.get();

    stopWatch.stop();
    log("Total Time Taken : " + stopWatch.getTime());
    executorService.shutdown();
    return new Product(productId, productInfo, review);
  }
}
