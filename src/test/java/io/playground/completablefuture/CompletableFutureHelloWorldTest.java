package io.playground.completablefuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.playground.service.HelloWorldService;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class CompletableFutureHelloWorldTest {

  HelloWorldService hws = new HelloWorldService();
  CompletableFutureHelloWorld cfhw = new CompletableFutureHelloWorld(hws);

  @Test
  void helloWorldAsyncCalls() {
    // Given

    // When
    String helloWorld = cfhw.helloWorldAsyncCalls();

    // Then
    assertEquals("HELLO WORLD!", helloWorld);
  }

  @Test
  void helloWorldMultipleAsyncCalls() {
    // Given

    // When
    String helloWorld = cfhw.helloWorldMultipleAsyncCalls();

    // Then
    assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", helloWorld);
  }
}