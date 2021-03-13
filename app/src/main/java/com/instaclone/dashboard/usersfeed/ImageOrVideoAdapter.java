package com.instaclone.dashboard.usersfeed;

import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.instaclone.R;
import com.instaclone.network.response.MyFeeds;
import com.instaclone.network.response.UserFeed;
import com.instaclone.utils.VideoPlayerConfig;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.Config;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

public class ImageOrVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int IMAGE_TYPE = 1;
    private static final int VIDEO_TYPE = 2;

    private List<UserFeed.Feed.Meta> imageList;

    public ImageOrVideoAdapter(List<UserFeed.Feed.Meta> imageList) {
        this.imageList = imageList;
    }

    public void update(List<UserFeed.Feed.Meta> imageList) {
        this.imageList = new ArrayList<>();
        this.imageList.addAll(imageList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_row, parent, false);

            return new ImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vh_video_exoplayer_basic, parent, false);

            return new SimpleExoVideoPlayerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        UserFeed.Feed.Meta meta = imageList.get(position);

        if (TextUtils.isEmpty(meta.getVideo())) {

            ((ImageViewHolder) holder).bind(imageList.get(position));

        } else {
            ((SimpleExoVideoPlayerViewHolder) holder).bind(this, imageList.get(position), null);
        }

//        if (imageList.get(position).postType == 1) {
//
//            ((ImageViewHolder) holder).bind(imageList.get(position));
//
//        } else {
//
//            ((SimpleExoVideoPlayerViewHolder) holder).bind(this, imageList.get(position), null);
//
//        }

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        UserFeed.Feed.Meta meta = imageList.get(position);

        if (TextUtils.isEmpty(meta.getVideo())) {
            return IMAGE_TYPE;
        }

        return VIDEO_TYPE;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.post_image)
        ImageView postImage;

        ImageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(UserFeed.Feed.Meta image) {

            postImage.layout(0, 0, 0, 0);

            Glide.with(postImage.getContext())
                    .load(image.getMedia())
                    .error(R.drawable.bg_img)
                    .placeholder(R.drawable.bg_img)
                    .apply(new RequestOptions().centerCrop())
                    .into(postImage);

//            Picasso.get().load(image.getMedia())
////                    .resize(0, 600)
////                    .centerCrop()
//                    .fit()
//                    .into(postImage);

//            Picasso.get().load(image.getMedia())
//                    .fit().into(postImage);
//
//            Glide.with(postImage.getContext())
//                    .load(image.getMedia())
//                    .thumbnail(0.1f)
////                    .fitCenter()
////                    .apply(new RequestOptions().override(1200, 800))
////                    .centerCrop()
//                    .into(postImage);


        }
    }

    public static class SimpleExoVideoPlayerViewHolder extends RecyclerView.ViewHolder implements ToroPlayer {

        static final int LAYOUT_RES = R.layout.vh_video_exoplayer_basic;

        @Nullable
        ExoPlayerViewHelper helper;
        EventListener listener;
        @Nullable
        private Uri mediaUri;


        @BindView(R.id.player)
        PlayerView playerView;
        @BindView(R.id.img)
        ImageView postImage;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        SimpleExoVideoPlayerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            playerView = itemView.findViewById(R.id.player);
//
//            progressBar = itemView.findViewById(R.id.progress_bar);
//            postImage = itemView.findViewById(R.id.img);

            playerView.setShutterBackgroundColor(Color.TRANSPARENT);

//            if (selector != null) {
//
//                playerView.setControlDispatcher(new ExoPlayerDispatcher(selector, this));
//
//
//            }


        }

        // called from Adapter to setup the media
        void bind(@NonNull RecyclerView.Adapter adapter, UserFeed.Feed.Meta image, List<Object> payloads) {

//            MyFeeds.Feed.Image image = item.video;

            if (image != null) {
                mediaUri = Uri.parse(image.getVideo());
                postImage.layout(0, 0, 0, 0);
                Glide.with(postImage.getContext())
                        .load(image.getMedia())
                        .error(R.drawable.bg_img)
                        .placeholder(R.drawable.bg_img)
                        .apply(new RequestOptions().centerCrop())
                        .into(postImage);
                postImage.setVisibility(View.VISIBLE);
//            playerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
//                playerView.setMinimumWidth(postImage.getWidth());
//                playerView.setMinimumHeight(postImage.getHeight());
            }

//        selector.toPause(getAdapterPosition());

            if (!isPlaying()) {
                progressBar.setVisibility(View.GONE);
            }


//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    helper.play();
//                    playerView.getPlayer().setPlayWhenReady(true);
////                if (helper != null && selector != null && !helper.isPlaying()) {
//                    //                    helper.play();
//////                    selector.toPlay(getAdapterPosition());
////                } else if (helper != null && selector != null && helper.isPlaying()) {
//////                    selector.toPause(getAdapterPosition());
////                    helper.pause();
////                }
//
//                }
//            });
        }


        @NonNull
        @Override
        public View getPlayerView() {
            return playerView;
        }

        @NonNull
        @Override
        public PlaybackInfo getCurrentPlaybackInfo() {
            return helper != null ? helper.getLatestPlaybackInfo() : new PlaybackInfo();
        }

        private static final String TAG = "SimpleExoPlayerViewHold";

        @Override
        public void initialize(@NonNull Container container, @Nullable PlaybackInfo playbackInfo) {
            if (helper == null) {
//                helper = new ExoPlayerViewHelper(this, mediaUri);

//                LoadControl myLoadControl = new DefaultLoadControl.Builder()
//                        // add your custom setup here
//                        .createDefaultLoadControl();

//// If you want to extends/modify current Config
//                Config modifiedConfig = Config.newBuilder()
//                        .setLoadControl(myLoadControl).build();

// If you want to create new Config
//                Config newConfig = new Config.Builder(itemView.getContext() /* Context */)
//                        .setLoadControl(myLoadControl).build();

                LoadControl loadControl = new DefaultLoadControl.Builder()
                        .setAllocator(new DefaultAllocator(true, 16))
                        .setBufferDurationsMs(VideoPlayerConfig.MIN_BUFFER_DURATION,
                                VideoPlayerConfig.MAX_BUFFER_DURATION,
                                VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                                VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER)
                        .setTargetBufferBytes(-1)
                        .setPrioritizeTimeOverSizeThresholds(true).createDefaultLoadControl();

                Config newConfig = new Config.Builder().setLoadControl(loadControl).build();

                helper = new ExoPlayerViewHelper(this, mediaUri, null, newConfig);

            }


            if (listener == null) {
                listener = new EventListener() {
                    @Override
                    public void onFirstFrameRendered() {
                        if (playerView != null && playerView.getPlayer() != null)
                            playerView.getPlayer().setRepeatMode(Player.REPEAT_MODE_ONE);
//                    playerView.setVisibility(View.GONE);
//                    imageView.setVisibility(View.INVISIBLE);
//                    playerView.setVisibility(View.VISIBLE);
//                    playerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        postImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onBuffering() {
//                    playerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
//                    playerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onPlaying() {
//
                        progressBar.setVisibility(View.GONE);
                        postImage.setVisibility(View.GONE);
                        playerView.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onPaused() {

                    }

                    @Override
                    public void onCompleted() {
//                        progressBar.setVisibility(View.GONE);
//                        postImage.setVisibility(View.VISIBLE);
                    }
                };
                helper.addPlayerEventListener(listener);
            }

            helper.initialize(container, playbackInfo);

        }

        @Override
        public void release() {
            progressBar.setVisibility(View.GONE);
            playerView.setVisibility(View.INVISIBLE);
            postImage.setVisibility(View.VISIBLE);
            if (helper != null) {

                if (listener != null) {
                    helper.removePlayerEventListener(listener);
                    listener = null;
                }

                helper.release();
                helper = null;
            }
        }

        @Override
        public void play() {
            if (helper != null) helper.play();

//        if (helper != null) helper.setVolume(1f);
//            playerView.getPlayer().getAudioComponent().setVolume(1f);
        }

        @Override
        public void pause() {
            if (helper != null) helper.pause();
        }


        @Override
        public boolean isPlaying() {
            return helper != null && helper.isPlaying();
        }

        @Override
        public boolean wantsToPlay() {
//        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 1;
//            return false;//ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
            return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
        }

        @Override
        public int getPlayerOrder() {
            return getAdapterPosition();
        }
    }
}
