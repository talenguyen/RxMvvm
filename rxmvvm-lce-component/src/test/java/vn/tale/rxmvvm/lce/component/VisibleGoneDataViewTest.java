package vn.tale.rxmvvm.lce.component;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Created by Giang Nguyen on 6/13/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class VisibleGoneDataViewTest {

  @Mock View view;
  public VisibleGoneDataView<String> visibleGoneView;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    visibleGoneView = new VisibleGoneDataView<String>(view) {
      @Override public void setData(String data) {
        // NoOps
      }
    };
  }

  @Test
  public void testShow() throws Exception {
    visibleGoneView.show();
    verify(view).setVisibility(View.VISIBLE);
  }

  @Test
  public void testHide() throws Exception {
    visibleGoneView.hide();
    verify(view).setVisibility(View.GONE);
  }
}