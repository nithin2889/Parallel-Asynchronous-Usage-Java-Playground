package io.playground.service;

import static io.playground.util.CommonUtil.startTimer;
import static io.playground.util.CommonUtil.stopWatchReset;
import static io.playground.util.CommonUtil.timeTaken;

import io.playground.domain.checkout.Cart;
import io.playground.domain.checkout.CartItem;
import io.playground.domain.checkout.CheckoutResponse;
import io.playground.domain.checkout.CheckoutStatus;
import java.util.List;
import java.util.stream.Collectors;

public class CheckoutService {

  private PriceValidatorService priceValidatorService;

  public CheckoutService(PriceValidatorService priceValidatorService) {
    this.priceValidatorService = priceValidatorService;
  }

  public CheckoutResponse checkout(Cart cart) {
    startTimer();
    List<CartItem> priceValidationList =
        cart.getCartItemList().parallelStream()
            .map(
                cartItem -> {
                  boolean isExpired = priceValidatorService.isCartItemInvalid(cartItem);
                  cartItem.setExpired(isExpired);
                  return cartItem;
                })
            .filter(CartItem::isExpired)
            .collect(Collectors.toList());

    timeTaken();
    stopWatchReset();

    if (!priceValidationList.isEmpty()) {
      return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
    }
    return new CheckoutResponse(CheckoutStatus.SUCCESS);
  }
}
