package students;

import java.util.ArrayList;
import java.util.List;

interface CriterionOfStudent {
  boolean test(Student s); // exactly one abstract method
}

class SmartStudent implements CriterionOfStudent {
  private int threshold;

  public SmartStudent(int threshold) {
    this.threshold = threshold;
  }

  @Override
  public boolean test(Student s) {
    return s.getGrade() > threshold;
  }
}

class EnthusiasticStudent implements CriterionOfStudent {
  @Override
  public boolean test(Student s) {
    return s.getCourses().size() > 3;
  }
}

public class School {
  public static void showAll(List<Student> students) {
    for (Student s : students) {
      System.out.println(s);
    }
    System.out.println("----------------------------");
  }

//  public static List<Student> getSmart(List<Student> students, int threshold) {
//    List<Student> rv = new ArrayList<>();
//    for (Student s : students) {
//      if (s.getGrade() > threshold) {
//        rv.add(s);
//      }
//    }
//    return rv;
//  }
//
//  public static List<Student> getEnthusiastic(List<Student> students, int threshold) {
//    List<Student> rv = new ArrayList<>();
//    for (Student s : students) {
//      if (s.getCourses().size() > threshold) {
//        rv.add(s);
//      }
//    }
//    return rv;
//  }

  public static List<Student> getByCriterion(List<Student> students, CriterionOfStudent crit) {
    List<Student> rv = new ArrayList<>();
    for (Student s : students) {
      if (crit.test(s)) {
        rv.add(s);
      }
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
//    showAll(getSmart(roster, 65));
//    showAll(getEnthusiastic(roster, 2));
    showAll(getByCriterion(roster, new SmartStudent(80)));
    showAll(getByCriterion(roster, new EnthusiasticStudent()));
//      showAll(getByCriterion(roster, new
//              /*class EnthusiasticStudent implements */CriterionOfStudent() {
//            @Override
//            public boolean test(Student s) {
//              return s.getCourses().size() > 3;
//            }
//          }
//      ));
//      showAll(getByCriterion(roster, /*new
//              CriterionOfStudent() {
//            @Override
//            public boolean test*/(Student s) -> {
//              return s.getCourses().size() > 3;
//            }
//          /*}*/
//      ));

//    showAll(getByCriterion(roster, (Student s) -> {
//      return s.getCourses().size() < 3;
//    }));
//
    showAll(getByCriterion(roster, (s) -> {
      return s.getCourses().size() < 3;
    }));

    CriterionOfStudent midRange = (Student s) -> {
      return s.getGrade() < 80 && s.getGrade() > 70;
    };

    /// NO NO NO! Must be an interface context, and
    // interface must define EXACTLY ONE abstract method
    // And, that must be the ONLY method we wish to implement.
//    Object midRange = (Student s) -> {
//      return s.getGrade() < 80 && s.getGrade() > 70;
//    };

    showAll(getByCriterion(roster, midRange));
  }
}

