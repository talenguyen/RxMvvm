package vn.tale.rxmvvm_sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.tale.rxmvvm_sample.R;
import vn.tale.rxmvvm_sample.ui.users.UsersFragment;

/**
 * Created by Giang Nguyen on 6/12/16.
 */
public class UsersActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_single);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.content, new UsersFragment())
          .commit();
    }
  }
}
