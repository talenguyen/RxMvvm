package vn.tale.rxmvvm_sample.model;

import java.util.List;

import rx.Observable;
import vn.tale.rxmvvm_sample.api.GitHubService;

/**
 * Created by Giang Nguyen at Tiki on 6/9/16.
 */
public class UserModel {

    private final GitHubService gitHubService;
    private int count = 0;

    public UserModel(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    public Observable<List<User>> getUsers() {
        count++;
        if (count % 3 == 0) {
            return Observable.just(null);
        } else if (count % 3 == 1) {
            return Observable.error(new RuntimeException("Error"));
        }
        return gitHubService.getUsers();
    }
}
