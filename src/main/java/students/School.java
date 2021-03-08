package students;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
interface CriterionOfStudent {
  boolean test(Student s); // exactly one abstract method
//  void doStuff();
  // not accessible inside a lambda because IF "this" exists,
  // it does NOT refer to our lambda object.
  default int value() { return 3; }

  static CriterionOfStudent negate(CriterionOfStudent crit) {
    return s -> !crit.test(s);
  }

  default CriterionOfStudent negate() {
    return s -> !this.test(s);
  }

  default CriterionOfStudent and(CriterionOfStudent other) {
    return s -> this.test(s) && other.test(s);
  }
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
      if (crit.test(s)) { // crit(s)?? NOPE
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
    // Java 10+ allows var for formal parameter "type"
    // useful for annotation (only)
//    showAll(getByCriterion(roster, (@Deprecated var s) -> {
//      return s.getCourses().size() < 3;
//    }));
// Specify ALL types explicitly, or all using var, or none of them...

    // can leave off parens, with SINGLE formal param if no var, and no type...
//    showAll(getByCriterion(roster, s -> {
//      return s.getCourses().size() < 3;
//    }));

    // "expression" lambda (if curlies, then it's a block lambda)
    showAll(getByCriterion(roster, s -> s.getCourses().size() < 3 ));

    CriterionOfStudent midRange = (Student s) -> {
      return s.getGrade() < 80 && s.getGrade() > 70;
    };

//    Object midRange = (CriterionOfStudent & Serializable)(Student s) -> {
//      return s.getGrade() < 80 && s.getGrade() > 70;
//    };

    Class<?> theClass = midRange.getClass();
    System.out.println("type of midRange: " + theClass);
    Method [] methods = theClass.getMethods();
    for (Method m : methods) {
      System.out.println("> " + m);
    }

    /// NO NO NO! Must be an interface context, and
    // interface must define EXACTLY ONE abstract method
    // And, that must be the ONLY method we wish to implement.
//    Object midRange = (Student s) -> {
//      return s.getGrade() < 80 && s.getGrade() > 70;
//    };

    showAll(getByCriterion(roster, midRange));

    int [] thresh = { 80 };
    CriterionOfStudent crit = Student.getSmartCriterion(thresh);
    showAll(getByCriterion(roster, crit));
    thresh[0] = 50;
    showAll(getByCriterion(roster, crit));

//    Integer i = null;
//    System.out.println(i.intValue());

    CriterionOfStudent enthusiastic = s -> s.getCourses().size() > 3;
    System.out.println("Enthusiastic");
    showAll(getByCriterion(roster,  enthusiastic));
    System.out.println("NOT Enthusiastic");
//    showAll(getByCriterion(roster,  CriterionOfStudent.negate(enthusiastic)));
    showAll(getByCriterion(roster,  enthusiastic.negate()));
    System.out.println("Smartish, and not enthusiastic");
    CriterionOfStudent smartish = s -> s.getGrade() > 70;
    showAll(getByCriterion(roster,  enthusiastic.negate().and(smartish)));

  }
}

