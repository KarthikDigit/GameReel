package com.instaclone.dashboard.followerandfollowing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.instaclone.R;
import com.instaclone.network.response.Following;
import com.instaclone.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FollowingListAdapter extends RecyclerView.Adapter<FollowingListAdapter.ViewHolder> {

    private Context mContext;
    private List<Following> mFollowingList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FollowingListAdapter(Context mContext, List<Following> mFollowingList) {
        this.mContext = mContext;
        this.mFollowingList = mFollowingList;
    }

    public void update(List<Following> mFollowingList) {

        this.mFollowingList = new ArrayList<>();
        this.mFollowingList.addAll(mFollowingList);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public FollowingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.following_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingListAdapter.ViewHolder holder, int position) {

        Following following = mFollowingList.get(position);

        holder.name.setText(following.getFullname());

        ImageLoader.loadImage(mContext, holder.photoImage, following.getPhoto());

    }

    @Override
    public int getItemCount() {
        return mFollowingList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_photo)
        CircleImageView photoImage;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.remove)
        TextView remove;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    interface OnItemClickListener {

        void OnItemClick(View view, Following following, int pos);

    }
}

