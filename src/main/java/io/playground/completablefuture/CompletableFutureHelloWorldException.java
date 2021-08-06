package io.playground.completablefuture;

import static io.playground.util.CommonUtil.delay;
import static io.playground.util.CommonUtil.startTimer;
import static io.playground.util.CommonUtil.timeTaken;
import static io.playground.util.LoggerUtil.log;

import io.playground.service.HelloWorldService;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorldException {

  private HelloWorldService helloWorldService;

  public CompletableFutureHelloWorldException(HelloWorldService helloWorldService) {
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

  public String helloWorldMultipleAsyncCallsHandle() {
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
            .handle((res, ex) -> {
              log("Exception is: " + ex.getMessage());
              return ""; // recoverable value in case of an exception
            })
            .thenCombine(world, (h, w) -> h + w)
            .thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr)
            .thenApply(String::toUpperCase)
            .join();
    timeTaken();
    return helloWorld;
  }

  public String helloWorldMultipleAsyncCallsHandlePositiveScenario() {
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
            .handle((res, ex) -> {
              log("Result is: " + res);
              if (ex != null) {
                log("Exception is: " + ex.getMessage());
                return ""; // recoverable value in case of an exception
              } else {
                return res;
              }
            })
            .thenCombine(world, (h, w) -> h + w)
            .thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr)
            .thenApply(String::toUpperCase)
            .join();
    timeTaken();
    return helloWorld;
  }

  public String helloWorldMultipleAsyncCallsExceptionally() {
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
            .exceptionally(ex -> {
              log("Exception is: " + ex.getMessage());
              return ""; // recoverable value in case of an exception
            })
            .thenCombine(world, (h, w) -> h + w)
            .exceptionally(ex -> {
              log("Exception after world is: " + ex.getMessage());
              return ""; // recoverable value in case of an exception
            })
            .thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr)
            .thenApply(String::toUpperCase)
            .join();
    timeTaken();
    return helloWorld;
  }

  public String helloWorldMultipleAsyncCallsWhenComplete() {
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
            .whenComplete((res, ex) -> {
              if (ex != null) {
                log("Exception is: " + ex.getMessage());
              }
            })
            .thenCombine(world, (h, w) -> h + w)
            .whenComplete((res, ex) -> {
              if (ex != null) {
                log("Exception after world is: " + ex.getMessage());
              }
            })
            .thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr)
            .thenApply(String::toUpperCase)
            .join();
    timeTaken();
    return helloWorld;
  }
}
