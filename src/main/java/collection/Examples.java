package collection;

import cleanedstudents.Student;
import org.apache.spark.sql.catalyst.expressions.JsonTuple;

import java.util.List;
import java.util.stream.Collectors;

public class Examples {
  public static String getLetterGrade(Student s) {
    int grade = s.getGrade();
    if (grade > 90) return "A";
    if (grade > 70) return "B";
    if (grade > 50) return "C";
    return "D";
  }
  public static void main(String[] args) {
    List<Student> roster = List.of(
        Student.of("Fred", 78, "Math", "Physics"),
        Student.of("Jim", 58, "Art"),
        Student.of("Joan", 73, "Art", "Journalism"),
        Student.of("Alice", 98, "Quantum Mechanics"),
        Student.of("Jimmy", 48, "History"),
        Student.of("Sheila", 89, "Math", "Physics", "Astro Physics", "Quantum Mechanics")
    );

    roster.stream()
//        .collect(Collectors.groupingBy(s -> Examples.getLetterGrade(s)))
        .collect(Collectors.groupingBy(Examples::getLetterGrade))
        .entrySet().stream()
        .forEach(e -> System.out.println("Students with grade " + e.getKey() +
            " are: " + e.getValue()));

    roster.stream()
        .collect(Collectors.groupingBy(
            Examples::getLetterGrade, Collectors.counting()))
        .entrySet().stream()
        .forEach(e -> System.out.println("There are " + e.getValue() +
            " students with grade " + e.getKey()));

    roster.stream()
        .collect(Collectors.groupingBy(
            Examples::getLetterGrade,
            Collectors.mapping(s -> s.getName(),
                Collectors.joining(", "))
            ))
        .entrySet().stream()
        .forEach(e -> System.out.println("Students with grade " + e.getKey() +
            " are " + e.getValue()));
  }
}
