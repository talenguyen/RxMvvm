package vn.tale.rxmvvm.lce.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import vn.tale.rxmvvm.lce.ShowHideData;

/**
 * Created by Giang Nguyen on 6/13/16.
 */
public class ShowHideToast implements ShowHideData<String> {

  private final Context context;
  private final int duration;
  public Toast toast;

  public ShowHideToast(Context context, int duration) {
    this.context = context;
    this.duration = duration;
  }

  @SuppressLint("ShowToast") @Override public void setData(String message) {
    toast = Toast.makeText(context, message, duration);
  }

  @Override public void show() {
    if (toast == null) {
      return;
    }
    toast.show();
  }

  @Override public void hide() {
    if (toast == null) {
      return;
    }
    toast.cancel();
  }
}
