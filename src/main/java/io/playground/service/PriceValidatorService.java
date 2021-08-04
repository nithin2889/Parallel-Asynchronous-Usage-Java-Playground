package io.playground.service;

import static io.playground.util.CommonUtil.delay;

import io.playground.domain.checkout.CartItem;

public class PriceValidatorService {

  public boolean isCartItemInvalid(CartItem cartItem) {
    int cartId = cartItem.getItemId();
    delay(500);
    if (cartId == 7 || cartId == 9 || cartId == 11) {
      return true;
    }
    return false;
  }
}
