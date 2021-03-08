package students;

import java.util.List;

public class School {
  public static void showAllSmart(List<Student> students) {
    for (Student s : students) {
      if (s.getGrade() > 65) {
        System.out.println(s);
      }
    }
    System.out.println("----------------------------");
  }

  public static void main(String[] args) {
    List<Student> roster = List.of(
        Student.of("Fred", 78, "Math", "Physics"),
        Student.of("Jim", 58, "Art"),
        Student.of("Sheila", 89, "Math", "Physics", "Astro Physics", "Quantum Mechanics")
    );
    showAllSmart(roster);
  }
}
