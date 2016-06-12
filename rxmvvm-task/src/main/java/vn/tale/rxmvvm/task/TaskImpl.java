package vn.tale.rxmvvm.task;

import java.util.List;
import java.util.NoSuchElementException;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;

/**
 * Created by Giang Nguyen on 6/12/16.
 */
class TaskImpl implements Task {
  private final Observable<Result.Success> success;
  private final Observable<Result.Error> error;
  private final ConnectableObservable<Void> loading;

  TaskImpl(Observable<?> source) {
    loading = Observable.<Void>just(null).publish();

    final Observable<Result> load = mapToErrorIfEmpty(source)
        .map(new Func1<Object, Result>() {
          @Override public Result call(Object data) {
            return new Result.Success(data);
          }
        })
        .onErrorReturn(new Func1<Throwable, Result>() {
          @Override public Result call(Throwable throwable) {
            return new Result.Error(throwable);
          }
        });

    final Observable<Result> result = loading.switchMap(new Func1<Void, Observable<Result>>() {
      @Override public Observable<Result> call(Void aVoid) {
        return load;
      }
    })
        .share();

    this.success = result.filter(new Func1<Result, Boolean>() {
      @Override public Boolean call(Result result) {
        return result instanceof Result.Success;
      }
    }).map(new Func1<Result, Result.Success>() {
      @Override public Result.Success call(Result result) {
        return (Result.Success) result;
      }
    });

    this.error = result.filter(new Func1<Result, Boolean>() {
      @Override public Boolean call(Result result) {
        return result instanceof Result.Error;
      }
    }).map(new Func1<Result, Result.Error>() {
      @Override public Result.Error call(Result result) {
        return (Result.Error) result;
      }
    });
  }

  @Override public Subscription start() {
    return loading.connect();
  }

  @Override public Observable<Void> loading() {
    return loading;
  }

  @Override public Observable<Result.Success> success() {
    return success;
  }

  @Override public Observable<Result.Error> error() {
    return error;
  }

  <Data> Observable<Data> mapToErrorIfEmpty(Observable<Data> source) {
    return source.filter(new Func1<Data, Boolean>() {
      @Override public Boolean call(Data data) {
        return data != null && !(data instanceof List && ((List) data).size() == 0);
      }
    })
    .switchIfEmpty(Observable.<Data>error(new NoSuchElementException()));
  }
}
