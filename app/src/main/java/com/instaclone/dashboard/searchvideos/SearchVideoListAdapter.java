package com.instaclone.dashboard.searchvideos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.instaclone.R;
import com.instaclone.network.response.SearchData;
import com.instaclone.utils.ImageLoader;
import com.instaclone.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchVideoListAdapter extends RecyclerView.Adapter<SearchVideoListAdapter.ViewHolder> {

    private Context mContext;
    private List<SearchData.Feed> mFeedList;
    private OnItemClickListener onItemClickListener;

    public SearchVideoListAdapter(Context mContext, List<SearchData.Feed> mFeedList) {
        this.mContext = mContext;
        this.mFeedList = mFeedList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SearchVideoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_image_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchVideoListAdapter.ViewHolder holder, int position) {

        SearchData.Feed feed = mFeedList.get(position);

        holder.bind(feed);

        holder.itemView.setOnClickListener(view -> {

            if (onItemClickListener != null)
                onItemClickListener.OnItemClick(view, feed, position);

        });

    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }

    public void updateList(List<SearchData.Feed> feedList) {

        this.mFeedList = new ArrayList<>();
        this.mFeedList.addAll(feedList);
        notifyDataSetChanged();

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

        public void bind(SearchData.Feed feed) {

//            Glide.with(photoImage.getContext())
//                    .load(feed.getMedia())
//                    .into(photoImage);

            ImageLoader.loadImageProgress(photoImage.getContext(), photoImage, feed.getMedia(), progressBar);


        }
    }

    public interface OnItemClickListener {

        void OnItemClick(View view, SearchData.Feed feed, int position);
    }
}
