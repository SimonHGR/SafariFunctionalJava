package superiterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SuperIterable<E> implements Iterable<E> {
  private Iterable<E> self;

  public SuperIterable(Iterable<E> self) {
    this.self = self;
  }


  // this is really "forEach"...
//  public void forEvery(Consumer<E> op) {
//    for (E s : self) {
//      op.accept(s);
//    }
//  }

  public SuperIterable<E> filter(Predicate<E> crit) {
    List<E> rv = new ArrayList<>();
    for (E s : self) {
      if (crit.test(s)) {
        rv.add(s);
      }
    }
    return new SuperIterable<>(rv);
  }

  public <F> SuperIterable<F> map(Function<E, F> op) {
    List<F> rv = new ArrayList<>();
    for (E s : self) {
      F f = op.apply(s);
      rv.add(f);
    }
    return new SuperIterable<>(rv);
  }

  public <F> SuperIterable<F> flatMap(Function<E, SuperIterable<F>> op) {
    List<F> rv = new ArrayList<>();
    for (E s : self) {
      SuperIterable<F> manyf = op.apply(s);
      for (F f : manyf) {
        rv.add(f);
      }
    }
    return new SuperIterable<>(rv);
  }

//  public SuperIterable<E> distinct() {
//
//  }

  public Iterator<E> iterator() {
    return self.iterator();
  }
}
