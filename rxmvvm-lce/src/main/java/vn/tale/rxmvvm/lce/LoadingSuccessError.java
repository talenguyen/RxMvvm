package vn.tale.rxmvvm.lce;

import rx.functions.Action1;

public interface LoadingSuccessError<Success, Error> {
  class Factory {
    private Factory() {
      // NoOps
    }
    public static <S, E> LoadingSuccessError<S, E> create(ShowHide loadingView,
                                                          ShowHideData<S> contentView,
                                                          ShowHideData<E> errorView) {
      return new LoadingContentErrorImpl<>(loadingView, contentView, errorView);
    }
  }

  Action1<Void> onLoading();
  Action1<Success> onSuccess();
  Action1<Error> onError();
}