package io.playground.completablefuture;

import static io.playground.util.CommonUtil.delay;
import static io.playground.util.CommonUtil.startTimer;
import static io.playground.util.CommonUtil.timeTaken;
import static io.playground.util.LoggerUtil.log;

import io.playground.service.HelloWorldService;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorld {

  private HelloWorldService helloWorldService;

  public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
    this.helloWorldService = helloWorldService;
  }

  public static void main(String[] args) {
    HelloWorldService helloWorldService = new HelloWorldService();

    CompletableFuture.supplyAsync(helloWorldService::helloWorld)
        .thenApply(String::toUpperCase)
        .thenAccept(result -> log("Result is: " + result));
    log("Done!");
    delay(2000);
  }

  public String helloWorld() {
    delay(1000);
    log("inside helloWorld");
    return "hello world";
  }

  public String hello() {
    delay(1000);
    log("inside hello");
    return "hello";
  }

  public String world() {
    delay(1000);
    log("inside world");
    return " world!";
  }

  public String helloWorldAsyncCalls() {
    startTimer();
    CompletableFuture<String> hello =
        CompletableFuture.supplyAsync(() -> helloWorldService.hello());
    CompletableFuture<String> world =
        CompletableFuture.supplyAsync(() -> helloWorldService.world());

    // first argument represents the result of hello.
    // second argument represents the result of world.
    // join() will block the main thread and returns the result as a string.
    String helloWorld =
        hello
            .thenCombine(world, (h, w) -> h + w)
            .thenApply(String::toUpperCase)
            .join();
    timeTaken();
    return helloWorld;
  }

  public String helloWorldMultipleAsyncCalls() {
    startTimer();
    CompletableFuture<String> hello =
        CompletableFuture.supplyAsync(() -> helloWorldService.hello());
    CompletableFuture<String> world =
        CompletableFuture.supplyAsync(() -> helloWorldService.world());
    CompletableFuture<String> hiCompletableFuture =
        CompletableFuture.supplyAsync(
            () -> {
              delay(1000);
              return " Hi CompletableFuture!";
            });

    // first argument represents the result of hello.
    // second argument represents the result of world.
    // join() will block the main thread and returns the result as a string.
    String helloWorld =
        hello
            .thenCombine(world, (h, w) -> h + w)
            .thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr)
            .thenApply(String::toUpperCase)
            .join();
    timeTaken();
    return helloWorld;
  }

  public CompletableFuture<String> helloWorldThenCompose() {
    return CompletableFuture.supplyAsync(helloWorldService::hello)
        .thenCompose(prev -> helloWorldService.worldFuture(prev))
        .thenApply(String::toUpperCase);
  }
}
