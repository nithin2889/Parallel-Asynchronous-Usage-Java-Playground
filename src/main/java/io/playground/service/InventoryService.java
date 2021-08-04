package io.playground.service;

import static io.playground.util.CommonUtil.delay;

import io.playground.domain.Inventory;
import io.playground.domain.ProductOption;
import java.util.concurrent.CompletableFuture;

public class InventoryService {
  public Inventory addInventory(ProductOption productOption) {
    delay(500);
    return Inventory.builder().count(2).build();
  }

  public CompletableFuture<Inventory> addInventory_CF(ProductOption productOption) {

    return CompletableFuture.supplyAsync(
        () -> {
          delay(500);
          return Inventory.builder().count(2).build();
        });
  }
}
