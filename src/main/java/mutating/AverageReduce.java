package mutating;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

class Average {
  private double sum = 0;
  private long count = 0;

  public Average() {}

  public Average(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public void include(double d) {
    sum += d;
    count++;
  }

  public void merge(Average other) {
    this.sum += other.sum;
    this.count += other.count;
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
//    ThreadLocalRandom.current().doubles(8_000_000_000L, -Math.PI, +Math.PI)
//    DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
    DoubleStream.iterate(0.0, x -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
        .unordered()
        .limit(2_000_000_000L)
        .parallel()
//        .collect(() -> new Average(), (r, d) -> r.include(d), (r1, r2) -> r1.merge(r2))
        .collect(Average::new, Average::include, Average::merge)
        .get()
        .ifPresent(v -> System.out.println("Average is " + v));

    long time = System.nanoTime() - start;
    System.out.println("time is " + (time / 1_000_000_000.0));
  }
}
