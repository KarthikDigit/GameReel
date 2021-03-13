package com.instaclone.dashboard.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.instaclone.R;
import com.instaclone.dashboard.usersfeed.UserFeedActivity;
import com.instaclone.network.response.MyPhotos;
import com.instaclone.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {

    private List<MyPhotos.Image> list;
    private Context mContext;

    public PhotoListAdapter(List<MyPhotos.Image> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void update(List<MyPhotos.Image> imageList) {

        this.list = new ArrayList<>();
        this.list.addAll(imageList);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public PhotoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoListAdapter.ViewHolder holder, int position) {

        MyPhotos.Image image = list.get(position);

        ImageLoader.loadImageProgress(mContext, holder.photoImage, image.getMedia(), holder.progressBar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UserFeedActivity.startUserActivity(view.getContext(), String.valueOf(image.getUserId()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photoImage)
        ImageView photoImage;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
