package nothis;

interface MyFunction {
  int apply(String s);
}

public class Example {
  public static void main(String[] args) {
//    MyFunction strlen = new MyFunction() {
//      @Override
//      public int apply(String s) {
//        if ("".equals(s)) return 0;
//        return 1 + this.apply(s.substring(1));
//      }
//    };
    new Example().doStuff();
  }
  public void doStuff() {
//    MyFunction strlen = (String s) -> {
//        if ("".equals(s)) return 0;
    // NO "this" created by/for a lambda, unlike anonymous inner...
//        return 1 + this.apply(s.substring(1));
//    };
//
//    System.out.println("Length of Hello is " + strlen.apply("Hello"));
  }
}
