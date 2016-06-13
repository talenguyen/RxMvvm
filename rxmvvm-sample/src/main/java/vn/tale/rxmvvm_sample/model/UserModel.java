package vn.tale.rxmvvm_sample.model;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;
import vn.tale.rxmvvm_sample.api.GitHubService;

/**
 * Created by Giang Nguyen at Tiki on 6/9/16.
 */
public class UserModel {
  private final GitHubService gitHubService;
  private int count = -1;

  public UserModel(GitHubService gitHubService) {
    this.gitHubService = gitHubService;
  }

  public Observable<List<User>> getUsers() {
    return Observable.defer(new Func0<Observable<List<User>>>() {
      @Override public Observable<List<User>> call() {
        count++;
        if (count % 3 == 0) {
          return Observable.timer(700, TimeUnit.MILLISECONDS)
              .flatMap(new Func1<Long, Observable<List<User>>>() {
                @Override public Observable<List<User>> call(Long aLong) {
                  return Observable.just(null);
                }
              });
        } else if (count % 3 == 1) {
          return Observable.timer(700, TimeUnit.MILLISECONDS)
              .flatMap(new Func1<Long, Observable<List<User>>>() {
                @Override public Observable<List<User>> call(Long aLong) {
                  return Observable.error(new RuntimeException("Error"));
                }
              });
        }
        return gitHubService.getUsers();
      }
    });
  }
}
