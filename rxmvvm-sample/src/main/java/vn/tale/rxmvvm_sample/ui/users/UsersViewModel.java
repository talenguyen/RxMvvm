package vn.tale.rxmvvm_sample.ui.users;

import java.util.List;
import java.util.NoSuchElementException;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import vn.tale.rxmvvm.task.Result;
import vn.tale.rxmvvm.task.Task;
import vn.tale.rxmvvm_sample.model.User;
import vn.tale.rxmvvm_sample.model.UserModel;

/**
 * Created by Giang Nguyen at Tiki on 6/8/16.
 */
public class UsersViewModel implements UsersMVVM.ViewModel {
  private final Task task;
  public final Observable<List<User>> loadUsersSuccess;
  public final Observable<String> loadUsersError;

  public UsersViewModel(final UserModel userModel, Scheduler scheduler) {
    final Observable<List<User>> getUsers = userModel.getUsers().subscribeOn(scheduler);
    task = Task.Factory.fromObservable(getUsers);
    loadUsersSuccess = task.success()
        .map(new Func1<Result.Success, List<User>>() {
          @Override public List<User> call(Result.Success success) {
            return success.getData();
          }
        });

    loadUsersError = task.error()
        .map(new Func1<Result.Error, String>() {
          @Override public String call(Result.Error error) {
            if (error.getThrowable() instanceof NoSuchElementException) {
              return "No Data";
            }
            return "Error occurred";
          }
        });
  }

  @Override public Observable<Void> loadingUsers() {
    return task.loading();
  }

  @Override
  public Observable<List<User>> loadUsersSuccess() {
    return loadUsersSuccess;
  }

  @Override
  public Observable<String> loadUsersError() {
    return loadUsersError;
  }

  @Override public void loadUsers() {
    task.start();
  }
}
