package vn.tale.rxmvvm.task;

import com.jakewharton.rxrelay.PublishRelay;

import java.util.List;
import java.util.NoSuchElementException;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Giang Nguyen on 6/12/16.
 */
class TaskImpl implements Task {
  private final Observable<Result.Success> success;
  private final Observable<Result.Error> error;
  private final Observable<Void> loading;
  private final PublishRelay<Void> start;

  TaskImpl(Observable<?> source) {
    start = PublishRelay.create();
    loading = start.switchMap(new Func1<Void, Observable<? extends Void>>() {
      @Override public Observable<? extends Void> call(Void aVoid) {
        return Observable.just(null);
      }
    });

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

    final Observable<Result> result = start.switchMap(new Func1<Void, Observable<Result>>() {
      @Override public Observable<Result> call(Void aVoid) {
        return load;
      }
    }).share();

    this.success = result.filter(new Func1<Result, Boolean>() {
      @Override public Boolean call(Result result) {
        return result instanceof Result.Success;
      }
    }).cast(Result.Success.class);

    this.error = result.filter(new Func1<Result, Boolean>() {
      @Override public Boolean call(Result result) {
        return result instanceof Result.Error;
      }
    }).cast(Result.Error.class);
  }

  @Override public void start() {
    start.call(null);
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
