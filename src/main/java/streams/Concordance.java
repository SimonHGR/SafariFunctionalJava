package streams;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Concordance {
  private static final Pattern WORD_BOUNDARY = Pattern.compile("\\W+");

  public static void main(String[] args) throws Throwable {
    try (Stream<String> in = Files.lines(Path.of("PrideAndPrejudice.txt"))) {
      in
//          .flatMap(line -> WORD_BOUNDARY.splitAsStream(line))
          .flatMap(WORD_BOUNDARY::splitAsStream)
          .filter(s -> s.length() > 0)
          .map(String::toLowerCase)
//          .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
          .entrySet().stream()
//          .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
          .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
          .limit(200)
          .map(e -> String.format("%20s : %5d", e.getKey(), e.getValue()))
          .forEach(System.out::println);
    }
  }
}
