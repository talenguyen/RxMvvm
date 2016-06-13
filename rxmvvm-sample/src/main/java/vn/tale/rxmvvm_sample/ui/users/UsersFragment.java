package vn.tale.rxmvvm_sample.ui.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import vn.tale.rxmvvm.lce.LoadingSuccessError;
import vn.tale.rxmvvm.lce.component.VisibleGoneDataView;
import vn.tale.rxmvvm.lce.component.VisibleGoneView;
import vn.tale.rxmvvm_sample.R;
import vn.tale.rxmvvm_sample.adapter.UsersAdapter;
import vn.tale.rxmvvm_sample.api.GitHubService;
import vn.tale.rxmvvm_sample.model.User;
import vn.tale.rxmvvm_sample.model.UserModel;

import static com.artemzin.rxui.RxUi.ui;

/**
 * Created by Giang Nguyen on 6/12/16.
 */
public class UsersFragment extends Fragment implements UsersMVVM.View {

  @BindView(R.id.rvList)
  RecyclerView rvList;
  @BindView(R.id.vProgress)
  ProgressBar vProgress;
  @BindView(R.id.tvError)
  TextView tvError;

  private final UsersAdapter adapter;
  private CompositeSubscription subscription;
  public UsersViewModel viewModel;
  private Func1<Observable<Void>, Subscription> onLoading;
  private Func1<Observable<List<User>>, Subscription> onSuccess;
  private Func1<Observable<String>, Subscription> onError;

  public UsersFragment() {
    adapter = new UsersAdapter();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.fragment_users, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupLce();
    final UserModel userModel = new UserModel(GitHubService.Factory.create());
    viewModel = new UsersViewModel(userModel, Schedulers.io());
    final UsersBinding usersBinding = new UsersBinding(viewModel);
    rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    rvList.setAdapter(adapter);
    subscription = new CompositeSubscription();
    subscription.add(usersBinding.bind(this));
    viewModel.loadUsers();
  }

  private void setupLce() {
    final VisibleGoneView loadingView = new VisibleGoneView(vProgress);
    final VisibleGoneDataView<List<User>> contentView = new VisibleGoneDataView<List<User>>(rvList) {
      @Override public void setData(List<User> users) {
        adapter.setUsers(users);
        adapter.notifyDataSetChanged();
      }
    };
    final VisibleGoneDataView<String> errorView = new VisibleGoneDataView<String>(tvError) {
      @Override public void setData(String s) {
        tvError.setText(s);
      }
    };
    final LoadingSuccessError<List<User>, String> lse = LoadingSuccessError.Factory.create(loadingView, contentView, errorView);
    onLoading = ui(lse.onLoading());
    onSuccess = ui(lse.onSuccess());
    onError = ui(lse.onError());
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (subscription != null) {
      subscription.unsubscribe();
    }
  }

  @OnClick(R.id.tvError) public void reload() {
    viewModel.loadUsers();
  }

  @Override public Func1<Observable<Void>, Subscription> onLoading() {
    return onLoading;
  }

  @Override
  public Func1<Observable<List<User>>, Subscription> onLoadUsersSuccess() {
    return onSuccess;
  }

  @Override
  public Func1<Observable<String>, Subscription> onLoadUsersError() {
    return onError;
  }
}
