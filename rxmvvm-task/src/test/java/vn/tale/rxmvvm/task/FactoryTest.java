package vn.tale.rxmvvm.task;

import org.junit.Test;

import rx.Observable;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Giang Nguyen on 6/12/16.
 */
public class FactoryTest {

  @Test
  public void testFromObservable() throws Exception {
    final Task data = Task.Factory.fromObservable(Observable.just("Data"));
    assertTrue(data instanceof TaskImpl);
  }
}