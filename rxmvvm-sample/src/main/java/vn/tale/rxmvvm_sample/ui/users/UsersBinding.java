package vn.tale.rxmvvm_sample.ui.users;

import com.artemzin.rxui.RxUi;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Giang Nguyen at Tiki on 6/9/16.
 */
public class UsersBinding {
    private final UsersMVVM.ViewModel viewModel;

    public UsersBinding(UsersMVVM.ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public Subscription bind(UsersMVVM.View view) {
        final CompositeSubscription subscription = new CompositeSubscription();
        subscription.add(RxUi.bind(viewModel.loadingUsers(), view.onLoading()));
        subscription.add(RxUi.bind(viewModel.loadUsersError(), view.onLoadUsersError()));
        subscription.add(RxUi.bind(viewModel.loadUsersSuccess(), view.onLoadUsersSuccess()));
        return subscription;
    }
}
