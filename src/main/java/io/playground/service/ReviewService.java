package io.playground.service;

import static io.playground.util.CommonUtil.delay;

import io.playground.domain.Review;

public class ReviewService {

  public Review retrieveReviews(String productId) {
    delay(1000);
    return new Review(200, 4.5);
  }
}
