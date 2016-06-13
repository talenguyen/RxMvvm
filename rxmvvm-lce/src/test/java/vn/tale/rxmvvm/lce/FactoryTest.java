package vn.tale.rxmvvm.lce;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mock;

/**
 * Created by Giang Nguyen on 6/13/16.
 */
public class FactoryTest {

  @Mock ShowHide loadingView;
  @Mock ShowHideData<String> contentView;
  @Mock ShowHideData<String> errorView;
  @Test
  public void testCreate() throws Exception {
    final LoadingSuccessError<String, String> loadingSuccessError
        = LoadingSuccessError.Factory.create(loadingView, contentView, errorView);
    Assert.assertTrue(loadingSuccessError instanceof LoadingContentErrorImpl);
  }
}