package vn.tale.rxmvvm_sample.ui.users;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import vn.tale.rxmvvm_sample.model.User;

/**
 * Created by Giang Nguyen at Tiki on 6/9/16.
 */
public interface UsersMVVM {
    interface View {
        Func1<Observable<Void>, Subscription> onLoading();
        Func1<Observable<List<User>>, Subscription> onLoadUsersSuccess();
        Func1<Observable<String>, Subscription> onLoadUsersError();
    }
    interface ViewModel {
        Observable<Void> loadingUsers();
        Observable<List<User>> loadUsersSuccess();
        Observable<String> loadUsersError();
        void loadUsers();
    }
}
