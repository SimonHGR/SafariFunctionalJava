package students;

import java.util.List;

public class Student {
  private String name;
  private int grade;
  private List<String> courses;

  private Student(String name, int grade, List<String> courses) {
    this.name = name;
    this.grade = grade;
    this.courses = courses;
  }

  public static Student of(String name, int grade, String ... courses) {
    return new Student(name, grade, List.of(courses));
  }

  public String getName() {
    return name;
  }

  public int getGrade() {
    return grade;
  }

  public List<String> getCourses() {
    return courses;
  }

  public static CriterionOfStudent getEnthusiasticCriterion(int threshold) {
    return s -> s.courses.size() > threshold;
  }

  // captured/closured local variables must be either final, or "effectively final"
  public static CriterionOfStudent getSmartCriterion(/*final*/ int[] threshold) {
//    return s -> s.getGrade() > 80;
//    threshold++;
    threshold[0] += 10;
    int [] myThresh = threshold.clone();
    return s -> s.grade > myThresh[0];
  }

  @Override
  public String toString() {
    return "Student{" +
        "name='" + name + '\'' +
        ", grade=" + grade +
        ", courses=" + courses +
        '}';
  }
}
