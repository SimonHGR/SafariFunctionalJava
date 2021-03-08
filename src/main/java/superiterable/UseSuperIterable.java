package superiterable;

import cleanedstudents.Student;

import java.util.List;

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

    SuperIterable<Student> roster = new SuperIterable<>(List.of(
        Student.of("Fred", 78, "Math", "Physics"),
        Student.of("Jim", 58, "Art"),
        Student.of("Sheila", 89, "Math", "Physics", "Astro Physics", "Quantum Mechanics")
    ));

    roster
        .filter(s -> s.getGrade() > 70)
        .map(s -> s.getName() + " has grade " + s.getGrade())
        .forEach(s -> System.out.println("> " + s));

    System.out.println("--------------------");
    roster
        .flatMap(s -> new SuperIterable<>(s.getCourses()))
//        .forEach(s -> System.out.println(s));
        .forEach(System.out::println);
  }
}
