package com.instaclone.dashboard.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.instaclone.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.HomeFeedViewHolder> {


    @NonNull
    @Override
    public HomeFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_feed_list_item, parent, false);

        return new HomeFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFeedViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class HomeFeedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_photo)
        CircleImageView profilePhoto;
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.ivEllipses)
        ImageView ivEllipses;
        @BindView(R.id.relLayout1)
        RelativeLayout relLayout1;
        @BindView(R.id.post_image)
        ImageView postImage;
        @BindView(R.id.image_heart_red)
        ImageView imageHeartRed;
        @BindView(R.id.image_heart)
        ImageView imageHeart;
        @BindView(R.id.heartLayout)
        RelativeLayout heartLayout;
        @BindView(R.id.speech_bubble)
        ImageView speechBubble;
        @BindView(R.id.relLayout2)
        RelativeLayout relLayout2;
        @BindView(R.id.image_likes)
        TextView imageLikes;
        @BindView(R.id.image_caption)
        TextView imageCaption;
        @BindView(R.id.image_comments_link)
        TextView imageCommentsLink;
        @BindView(R.id.image_time_posted)
        TextView imageTimePosted;
        @BindView(R.id.relLayout3)
        RelativeLayout relLayout3;

        HomeFeedViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
