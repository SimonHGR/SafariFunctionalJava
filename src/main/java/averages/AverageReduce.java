package averages;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

class Average {
  private double sum;
  private long count;

  public Average(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public Average include(double d) {
    return new Average(sum + d, count + 1);
  }

  public Average merge(Average other) {
    return new Average(this.sum + other.sum, this.count + other.count);
  }

  public Optional<Double> get() {
    if (count > 0) {
      return Optional.of(sum / count);
    } else {
      return Optional.empty();
    }
  }
}

public class AverageReduce {
  public static void main(String[] args) {
    long start = System.nanoTime();
//    DoubleStream.generate(() -> Math.random()) // Math.random is a big critical section
    ThreadLocalRandom.current().doubles(4_000_000_000L, -Math.PI, +Math.PI)
        .parallel()
        .boxed()
        .reduce(new Average(0, 0), (r, d) -> r.include(d), (r1, r2) -> r1.merge(r2))
        .get()
        .ifPresent(v -> System.out.println("Average is " + v));

    long time = System.nanoTime() - start;
    System.out.println("time is " + (time / 1_000_000_000.0));
  }
}
