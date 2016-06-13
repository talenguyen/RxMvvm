package vn.tale.rxmvvm.lce;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by Giang Nguyen on 6/13/16.
 */
public class LoadingContentErrorImplTest {

  @Mock ShowHide loadingView;
  @Mock ShowHideData<String> contentView;
  @Mock ShowHideData<String> errorView;
  public LoadingContentErrorImpl<String, String> lce;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    lce = new LoadingContentErrorImpl<>(loadingView, contentView, errorView);
  }

  @Test
  public void testShowLoading() throws Exception {
    lce.onLoading().call(null);
    verify(loadingView).show();
    verify(contentView).hide();
    verify(errorView).hide();
  }

  @Test
  public void testShowContent() throws Exception {
    lce.onSuccess().call("Data");
    verify(loadingView).hide();
    verify(contentView).setData(eq("Data"));
    verify(contentView).show();
    verifyZeroInteractions(errorView);
  }

  @Test
  public void testShowError() throws Exception {
    lce.onError().call("Error");
    verify(loadingView).hide();
    verify(errorView).setData(eq("Error"));
    verify(errorView).show();
    verifyZeroInteractions(contentView);

  }
}