package io.playground.service;

import static org.junit.jupiter.api.Assertions.*;

import io.playground.domain.checkout.Cart;
import io.playground.domain.checkout.CheckoutResponse;
import io.playground.domain.checkout.CheckoutStatus;
import io.playground.util.DataSet;
import org.junit.jupiter.api.Test;

class CheckoutServiceTest {

  PriceValidatorService priceValidatorService = new PriceValidatorService();
  CheckoutService checkoutService = new CheckoutService(priceValidatorService);

  @Test
  void numberOfCores() {
    System.out.println(Runtime.getRuntime().availableProcessors());
  }

  @Test
  void checkoutPositiveScenario() {
    // Given
    Cart cart = DataSet.createCart(4);

    // When
    CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

    // Then
    assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
  }

  @Test
  void checkoutNegativeScenario() {
    // Given
    Cart cart = DataSet.createCart(8);

    // When
    CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

    // Then
    assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
  }
}