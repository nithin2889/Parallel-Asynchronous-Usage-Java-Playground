package io.playground.parallelstreams;

import static io.playground.util.CommonUtil.startTimer;
import static io.playground.util.CommonUtil.stopWatchReset;
import static io.playground.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.playground.util.DataSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ParallelStreamsExampleTest {

  ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

  @Test
  void stringTransform() {
    // Given
    List<String> inputList = DataSet.namesList();

    // When
    startTimer();
    List<String> resultList = parallelStreamsExample.stringTransform(inputList);
    timeTaken();

    // Then
    assertEquals(4, resultList.size());
    resultList.forEach(name -> assertTrue(name.contains("-")));
  }

  @ParameterizedTest
  @ValueSource(booleans = {false, true})
  void stringTransform_2(boolean isParallel) {
    // Given
    List<String> inputList = DataSet.namesList();

    // When
    startTimer();
    List<String> resultList = parallelStreamsExample.stringTransform_2(inputList, isParallel);
    timeTaken();

    // Then
    assertEquals(4, resultList.size());
    resultList.forEach(name -> assertTrue(name.contains("-")));
    stopWatchReset();
  }
}
