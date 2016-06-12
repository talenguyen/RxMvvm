package vn.tale.rxmvvm.task;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


/**
 * Created by Giang Nguyen on 6/12/16.
 */
public class TaskImplTest {

  private TaskImpl task;

  @Before
  public void setUp() throws Exception {
    task = new TaskImpl(Observable.empty());
  }

  @Test
  public void testShouldNotErrorIfSourceHasData() throws Exception {
    final String expectedData = "Data";
    final Observable<String> errorIfEmpty = task.mapToErrorIfEmpty(Observable.just(expectedData));
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    errorIfEmpty.subscribe(testSubscriber);
    testSubscriber.assertValue(expectedData);
  }
  @Test
  public void testShouldBeErrorIfSourceEmpty() throws Exception {
    final Observable<String> errorIfEmpty = task.mapToErrorIfEmpty(Observable.<String>empty());
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    errorIfEmpty.subscribe(testSubscriber);
    testSubscriber.assertError(NoSuchElementException.class);
  }
  @Test
  public void testShouldBeErrorIfSourceNull() throws Exception {
    final Observable<String> errorIfEmpty = task.mapToErrorIfEmpty(Observable.<String>just(null));
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    errorIfEmpty.subscribe(testSubscriber);
    testSubscriber.assertError(NoSuchElementException.class);
  }
  @Test
  public void testShouldBeErrorIfSourceEmptyList() throws Exception {
    final TaskImpl lseViewModel = new TaskImpl(Observable.<List<String>>empty());
    final Observable<List<String>> errorIfEmpty = lseViewModel.mapToErrorIfEmpty(Observable.just(Collections.<String>emptyList()));
    final TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();
    errorIfEmpty.subscribe(testSubscriber);
    testSubscriber.assertError(NoSuchElementException.class);
  }

  @Test
  public void testShouldReceiveSuccess() throws Exception {
    final String expectedData = "Data";
    final TaskImpl lseViewModel = new TaskImpl(Observable.just(expectedData));
    final TestSubscriber<Result.Success> successSubscriber = new TestSubscriber<>();
    final TestSubscriber<Result.Error> failureSubscriber = new TestSubscriber<>();

    lseViewModel.success()
        .subscribe(successSubscriber);
    lseViewModel.error()
        .subscribe(failureSubscriber);
    lseViewModel.start();

    failureSubscriber.assertNoValues();
    successSubscriber.assertValueCount(1);
    final Result.Success receivedSuccess = successSubscriber.getOnNextEvents().get(0);
    final String successResult = receivedSuccess.getData();
    assertEquals(expectedData, successResult);
  }

  @Test
  public void testShouldReceiveError() throws Exception {
    final TaskImpl lseViewModel = new TaskImpl(Observable.<String>just(null));
    final TestSubscriber<Result.Success> successSubscriber = new TestSubscriber<>();
    final TestSubscriber<Result.Error> failureSubscriber = new TestSubscriber<>();

    lseViewModel.success()
        .subscribe(successSubscriber);
    lseViewModel.error()
        .subscribe(failureSubscriber);
    lseViewModel.start();

    successSubscriber.assertNoValues();
    failureSubscriber.assertValueCount(1);
    final Result.Error error = failureSubscriber.getOnNextEvents().get(0);
    assertTrue(error.getThrowable() instanceof NoSuchElementException);
  }

  @Test
  public void testShouldReceiveLoading() throws Exception {
    final TaskImpl lseViewModel = new TaskImpl(Observable.empty());
    final TestSubscriber<Void> loadingSubscriber = new TestSubscriber<>();
    lseViewModel.loading()
        .subscribe(loadingSubscriber);
    lseViewModel.start();
    loadingSubscriber.assertValue(null);
  }

  @Test
  public void testShouldReceiveMultipleLoading() throws Exception {
    final TaskImpl lseViewModel = new TaskImpl(Observable.empty());
    final TestSubscriber<Void> loadingSubscriber = new TestSubscriber<>();
    lseViewModel.loading()
        .subscribe(loadingSubscriber);
    lseViewModel.start();
    lseViewModel.start();
    loadingSubscriber.assertValueCount(2);
  }

  @Test
  public void testShouldReceiveMultipleSuccess() throws Exception {
    final TaskImpl lseViewModel = new TaskImpl(Observable.just("Data"));
    final TestSubscriber<Result.Success> successSubscriber = new TestSubscriber<>();
    lseViewModel.success()
        .subscribe(successSubscriber);
    lseViewModel.start();
    lseViewModel.start();
    successSubscriber.assertValueCount(2);
  }

}