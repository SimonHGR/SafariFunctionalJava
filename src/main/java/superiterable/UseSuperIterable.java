package superiterable;

import cleanedstudents.Student;

import java.util.List;
import java.util.stream.Stream;

public class UseSuperIterable {
  public static void main(String[] args) {
    SuperIterable<String> sis = new SuperIterable<>(List.of(
        "Fred", "Jim", "Sheila"
    ));

//    for (String s : sis) {
//      System.out.println("> " + s);
//    }
//
//    sis.showAll();

    List<Student> rosterList = List.of(
        Student.of("Fred", 78, "Math", "Physics"),
        Student.of("Jim", 58, "Art"),
        Student.of("Sheila", 89, "Math", "Physics", "Astro Physics", "Quantum Mechanics")
    );
    SuperIterable<Student> roster = new SuperIterable<>(rosterList);

    roster
        .filter(s -> s.getGrade() > 70)
        .map(s -> s.getName() + " has grade " + s.getGrade())
        .forEach(s -> System.out.println("> " + s));

    System.out.println("--------------------");
    rosterList.stream()
        .filter(s -> s.getGrade() > 70)
        .map(s -> s.getName() + " has grade " + s.getGrade())
        .forEach(s -> System.out.println("> " + s));

    System.out.println("--------------------");
    roster
        .flatMap(s -> new SuperIterable<>(s.getCourses()))
//        .forEach(s -> System.out.println(s));
        .forEach(System.out::println);
    System.out.println("--------------------");
    rosterList.stream()
        .flatMap(s -> s.getCourses().stream())
//        .forEach(s -> System.out.println(s));
        .forEach(System.out::println);
    System.out.println("--------------------");
    roster
        .peek(s -> System.out.println("SIS peek 1 " + s))
        .filter(s -> s.getGrade() > 70)
        .peek(s -> System.out.println("SIS peek 2 " + s))
        .flatMap(s ->
            new SuperIterable<>(s.getCourses()).map(c -> "Student: " + s.getName() + " takes " + c))
        .forEach(System.out::println);
    System.out.println("--------------------");
    rosterList.stream()
        .peek(s -> System.out.println("stream peek 1 " + s))
        .filter(s -> s.getGrade() > 70)
        .peek(s -> System.out.println("stream peek 2 " + s))
        .flatMap(s ->
            s.getCourses().stream().map(c -> "Student: " + s.getName() + " takes " + c))
        .forEach(System.out::println);
    System.out.println("--------------------");

    Stream<Student> ss = rosterList.stream();
    ss.forEach(System.out::println);
//    ss.forEach(System.out::println); // Stream is stateful and lazy
  }
}
