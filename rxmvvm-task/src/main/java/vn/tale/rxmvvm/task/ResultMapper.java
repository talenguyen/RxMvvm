package vn.tale.rxmvvm.task;

/**
 * Created by Giang Nguyen on 6/12/16.
 */
class ResultMapper {
  public Result.Success success(Object object) {
    return new Result.Success(object);
  }

  public Result.Error error(Throwable throwable) {
    return new Result.Error(throwable);
  }
}
