package usestreams;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InfiniteExample {
  public static void main(String[] args) {
    IntStream.iterate(1, x -> x + 1)
        .limit(10)
        .mapToObj(i -> "Number " + i)
        .forEach(System.out::println);
    IntStream is = IntStream.of(10);
    System.out.println(is.getClass());
  }
}
