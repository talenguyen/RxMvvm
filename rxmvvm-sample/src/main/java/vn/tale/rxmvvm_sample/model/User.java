package vn.tale.rxmvvm_sample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giang Nguyen at Tiki on 6/8/16.
 */
public class User {

    @SerializedName("login")
    private final String userName;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
