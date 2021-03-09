package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

public class Concordance {
  private static final Comparator<Tuple2<String, Long>> serializableTupleCompareLongReversed =
      (Serializable & Comparator<Tuple2<String, Long>>)(t1, t2) -> t2._2.compareTo(t1._2);
  public static void main(String[] args) {
    Pattern WORD_BOUNDARY = Pattern.compile("\\W+");
    SparkConf conf = new SparkConf().setAppName("Simple").setMaster("local");
    JavaSparkContext context = new JavaSparkContext(conf);

    context.textFile("PrideAndPrejudice.txt")
        .map(String::toLowerCase)
        .flatMap(l -> Arrays.asList(WORD_BOUNDARY.split(l)).iterator())
        .filter(w -> w.length() > 0)
        .mapToPair(w -> new Tuple2<>(w, 1L))
        .reduceByKey((v1, v2) -> v1 + v2)
        .takeOrdered(200, serializableTupleCompareLongReversed)
        .stream()
        .map(t -> String.format("%20s : %5d", t._1, t._2))
        .forEach(x -> System.out.println(x));
    context.close();
  }
}
