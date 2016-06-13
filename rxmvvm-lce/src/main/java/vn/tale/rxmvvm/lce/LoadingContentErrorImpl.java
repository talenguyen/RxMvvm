package vn.tale.rxmvvm.lce;

import rx.functions.Action1;

/**
 * Created by Giang Nguyen on 6/13/16.
 */
public class LoadingContentErrorImpl<Success, Error> implements LoadingSuccessError<Success, Error> {

  private final ShowHide loading;
  private final ShowHideData<Success> content;
  private final ShowHideData<Error> error;

  public LoadingContentErrorImpl(ShowHide loading,
                                 ShowHideData<Success> content,
                                 ShowHideData<Error> error) {
    this.loading = loading;
    this.content = content;
    this.error = error;
  }


  @Override public Action1<Void> onLoading() {
    return new Action1<Void>() {
      @Override public void call(Void aVoid) {
        error.hide();
        content.hide();
        loading.show();
      }
    };
  }

  @Override public Action1<Success> onSuccess() {
    return new Action1<Success>() {
      @Override public void call(Success success) {
        loading.hide();
        content.setData(success);
        content.show();
      }
    };
  }

  @Override public Action1<Error> onError() {
    return new Action1<Error>() {
      @Override public void call(Error e) {
        loading.hide();
        error.setData(e);
        error.show();
      }
    };
  }
}
