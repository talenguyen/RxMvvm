package vn.tale.rxmvvm_sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import vn.tale.rxmvvm_sample.model.User;
import vn.tale.rxmvvm_sample.viewholder.UserViewHolder;


/**
 * Created by Giang Nguyen at Tiki on 6/8/16.
 */
public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return UserViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final User user = users.get(position);
        final UserViewHolder userViewHolder = UserViewHolder.class.cast(holder);
        userViewHolder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }
}
