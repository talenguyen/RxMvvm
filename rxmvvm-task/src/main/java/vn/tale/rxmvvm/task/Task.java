package vn.tale.rxmvvm.task;

import rx.Observable;
import rx.Subscription;

/**
 * Created by Giang Nguyen at Tiki on 6/10/16.
 */
public interface Task {

  Subscription start();

  Observable<Void> loading();

  Observable<Result.Success> success();

  Observable<Result.Error> error();

  class Factory {
    private Factory() {
      // NoOps
    }

    public static Task fromObservable(Observable<?> observable) {
      return new TaskImpl(observable);
    }
  }
}
