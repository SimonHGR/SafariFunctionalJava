package cleanedstudents;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

//@FunctionalInterface
//interface Criterion<E> {
//  boolean test(E s); // exactly one abstract method
//
//  default Criterion<E> negate() {
//    return s -> !this.test(s);
//  }
//  default Criterion<E> and(Criterion<E> other) {
//    return s -> this.test(s) && other.test(s);
//  }
//}

public class School {
  //  public static <E> void showAll(List<E> students) {
  public static void showAll(Iterable<?> students) {
    for (Object s : students) {
      System.out.println(s);
    }
    System.out.println("----------------------------");
  }

//  public void sort(List<E> data, Ordering<E>)

  public static <E> List<E> filter(Iterable<E> data, Predicate<E> crit) {
    List<E> rv = new ArrayList<>();
    for (E s : data) {
      if (crit.test(s)) {
        rv.add(s);
      }
    }
    return rv;
  }

  public static <E, F> List<F> map(Iterable<E> data, Function<E, F> op) {
    List<F> rv = new ArrayList<>();
    for (E s : data) {
      F f = op.apply(s);
      rv.add(f);
    }
    return rv;
  }

  public static void main(String[] args) {
    List<Student> roster = List.of(
        Student.of("Fred", 78, "Math", "Physics"),
        Student.of("Jim", 58, "Art"),
        Student.of("Sheila", 89, "Math", "Physics", "Astro Physics", "Quantum Mechanics")
    );
    showAll(roster);

    Predicate<Student> enthusiastic = s -> s.getCourses().size() > 3;
    System.out.println("Enthusiastic");
    showAll(filter(roster, enthusiastic));

    List<String> words = List.of("banana", "apple", "pie", "custard", "date");
    showAll(filter(words, s -> s.length() > 4));

    words = new ArrayList<>(words);
//    words.sort((s1, s2) -> s2.compareTo(s1));
    words.sort((s1, s2) -> s1.compareTo(s2));
    words.sort(String::compareTo);
    System.out.println(words);
//    words.sort((s1, s2) -> s1.length() - s2.length());
    words.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));
    System.out.println(words);
    System.out.println("-----------------------------");
    showAll(map(roster, s -> "Student called " + s.getName() + " has grade " + s.getGrade()));
    showAll(map(roster, s -> s.getGrade()));
    showAll(map(words, s -> s.length()));

  }
}

