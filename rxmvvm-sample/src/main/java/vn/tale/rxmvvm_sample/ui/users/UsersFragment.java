package vn.tale.rxmvvm_sample.ui.users;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import vn.tale.rxmvvm_sample.R;
import vn.tale.rxmvvm_sample.adapter.UsersAdapter;
import vn.tale.rxmvvm_sample.api.GitHubService;
import vn.tale.rxmvvm_sample.model.User;
import vn.tale.rxmvvm_sample.model.UserModel;

import static com.artemzin.rxui.RxUi.ui;

/**
 * Created by Giang Nguyen at Tiki on 6/8/16.
 */
public class UsersFragment extends Fragment implements UsersMVVM.View {

    @BindView(R.id.rvList)
    RecyclerView rvList;

    private UsersAdapter adapter;
    private CompositeSubscription subscription;
    public ProgressDialog loadingDialog;

    public UsersFragment() {
        adapter = new UsersAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final UserModel userModel = new UserModel(GitHubService.Factory.create());
        final UsersViewModel viewModel = new UsersViewModel(userModel, Schedulers.io());
        final UsersBinding usersBinding = new UsersBinding(viewModel);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvList.setAdapter(adapter);
        subscription = new CompositeSubscription();
        subscription.add(usersBinding.bind(UsersFragment.this));
        viewModel.loadUsers();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        subscription.unsubscribe();
    }

    @Override public Func1<Observable<Void>, Subscription> onLoading() {
        return ui(new Action1<Void>() {
            @Override public void call(Void aVoid) {
                showLoading();
            }
        });
    }

    private void showLoading() {
        loadingDialog = ProgressDialog.show(getContext(), null, "Loading", true, false);
    }

    void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public Func1<Observable<List<User>>, Subscription> onLoadUsersSuccess() {
        return ui(new Action1<List<User>>() {
            @Override
            public void call(List<User> data) {
                hideLoading();
                adapter.setUsers(data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public Func1<Observable<String>, Subscription> onLoadUsersError() {
        return ui(new Action1<String>() {
            @Override
            public void call(String error) {
                hideLoading();
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
