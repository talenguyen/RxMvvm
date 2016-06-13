package vn.tale.rxmvvm.lce.component;

import android.view.View;

import vn.tale.rxmvvm.lce.ShowHideData;

/**
 * Created by Giang Nguyen on 6/13/16.
 */
public abstract class VisibleGoneDataView<Data> implements ShowHideData<Data> {
  private final View view;

  public VisibleGoneDataView(View view) {
    this.view = view;
  }

  @Override public void show() {
      view.setVisibility(View.VISIBLE);
  }

  @Override public void hide() {
    view.setVisibility(View.GONE);
  }
}
