package vn.tale.rxmvvm.lce.component;

import android.view.View;

import vn.tale.rxmvvm.lce.ShowHide;

/**
 * Created by Giang Nguyen on 6/13/16.
 */
public class VisibleGoneView implements ShowHide {
  private final View view;

  public VisibleGoneView(View view) {
    this.view = view;
  }

  @Override public void show() {
      view.setVisibility(View.VISIBLE);
  }

  @Override public void hide() {
    view.setVisibility(View.GONE);
  }
}
