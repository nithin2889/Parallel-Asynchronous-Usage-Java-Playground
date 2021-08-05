package io.playground.parallelstreams;

import static io.playground.util.CommonUtil.delay;
import static io.playground.util.CommonUtil.startTimer;
import static io.playground.util.CommonUtil.timeTaken;
import static io.playground.util.LoggerUtil.log;

import io.playground.util.DataSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelStreamsExample {

  public List<String> stringTransform(List<String> namesList) {
    return namesList
      .parallelStream()
      .map(this::addNameLengthTransform)
      .sequential()
      .collect(Collectors.toList());
  }

  public List<String> stringTransform_2(List<String> namesList, boolean isParallel) {
    Stream<String> namesStream = namesList.stream();

    if (isParallel) {
      namesStream.parallel();
    }

    return namesStream
      .map(this::addNameLengthTransform)
      .sequential()
      .collect(Collectors.toList());
  }

  public static void main(String[] args) {
    List<String> namesList = DataSet.namesList();
    ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();
    startTimer();
    List<String> resultList = parallelStreamsExample.stringTransform(namesList);
    log("resultList: " + resultList);
    timeTaken();
  }

  private String addNameLengthTransform(String name) {
    delay(500);
    return name.length() + " - " + name;
  }
}