package vn.tale.rxmvvm_sample.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import vn.tale.rxmvvm_sample.R;
import vn.tale.rxmvvm_sample.model.User;

/**
 * Created by Giang Nguyen at Tiki on 6/8/16.
 */
public class UserViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "UserViewHolder";
    private TextView tvUserName;

    public static UserViewHolder create(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    public UserViewHolder(View itemView) {
        super(itemView);
        tvUserName = ButterKnife.findById(itemView, R.id.tvUserName);
    }

    public void bind(User user) {
        tvUserName.setText(user.getUserName());
    }
}

