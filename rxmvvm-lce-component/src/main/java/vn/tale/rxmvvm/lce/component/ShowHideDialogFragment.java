package vn.tale.rxmvvm.lce.component;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;

import vn.tale.rxmvvm.lce.ShowHideData;

/**
 * Created by Giang Nguyen on 6/13/16.
 */
public class ShowHideDialogFragment<Data> implements ShowHideData<Data> {

  private final String tag;
  private final FragmentManager fragmentManager;
  private final DialogBuilder<Data> dialogBuilder;
  public Data data;

  public interface DialogBuilder<Data> {
    Fragment build(Data data);
  }

  public ShowHideDialogFragment(String tag, FragmentManager fragmentManager, DialogBuilder<Data> dialogBuilder) {
    this.tag = tag;
    this.fragmentManager = fragmentManager;
    this.dialogBuilder = dialogBuilder;
  }

  @Override public void setData(Data data) {
    this.data = data;
  }

  @Override
  public void show() {
    Fragment fragment = fragmentManager.findFragmentByTag(tag);
    if (!(fragment instanceof DialogFragment)) {
      fragment = dialogBuilder.build(data);
    }
    final DialogFragment dialogFragment = (DialogFragment) fragment;
    if (dialogFragment.isVisible()) {
      return;
    }
    fragmentManager.beginTransaction()
        .add(fragment, tag)
        .commitAllowingStateLoss();
  }

  @Override
  public void hide() {
    Fragment fragment = fragmentManager.findFragmentByTag(tag);
    if (!(fragment instanceof DialogFragment)) {
      return;
    }
    final DialogFragment dialogFragment = (DialogFragment) fragment;
    if (dialogFragment.isVisible()) {
      fragmentManager.beginTransaction()
          .remove(fragment)
          .commitAllowingStateLoss();
    }
  }
}
