package com.instaclone.dashboard.usersfeed;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chahinem.pageindicator.PageIndicator;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.instaclone.R;
import com.instaclone.dashboard.home.updatedview.ExtraPlaybackInfo;
import com.instaclone.dashboard.home.updatedview.ImageViewAdapter;
import com.instaclone.dashboard.profile.AnotherUserDetailsActivity;
import com.instaclone.network.response.MyFeeds;
import com.instaclone.network.response.UserFeed;
import com.instaclone.utils.ImageLoader;
import com.instaclone.utils.VideoPlayerConfig;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import im.ene.toro.CacheManager;
import im.ene.toro.PlayerSelector;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.Config;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

public class UserFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CacheManager {

    private Context mContext;
    private List<UserFeed.Feed> myFeedsList;

    public UserFeedAdapter(Context mContext, List<UserFeed.Feed> myFeedsList) {

        this.mContext = mContext;
        this.myFeedsList = myFeedsList;

    }

    public void update(List<UserFeed.Feed> feedsList) {

        this.myFeedsList = new ArrayList<>();
        this.myFeedsList.addAll(feedsList);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


//        if (viewType == 1) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_feed_list_item, parent, false);

        return new HomeFeedViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.vh_exoplayer_basic, parent, false);
//
//            return new SimpleExoPlayerViewHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        int type = myFeedsList.get(position).type;
//
//        if (type == 1) {

        ((HomeFeedViewHolder) holder).bind(myFeedsList.get(position));

//        } else if (type == 2) {
//
//            ((SimpleExoPlayerViewHolder) holder).bind(this, myFeedsList.get(position), null);
//
//        }

    }

    @Override
    public int getItemCount() {
        return myFeedsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
//        return myFeedsList.get(position).getType();
    }


    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof HomeFeedViewHolder) {
            ((HomeFeedViewHolder) holder).onDetached();
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof HomeFeedViewHolder) {
            ((HomeFeedViewHolder) holder).onAttached();
        }
    }

    @Nullable
    @Override
    public Object getKeyForOrder(int order) {
        return order;
    }

    @Nullable
    @Override
    public Integer getOrderForKey(@NonNull Object key) {
        return key instanceof Integer ? (Integer) key : null;
    }

    static class HomeFeedViewHolder extends RecyclerView.ViewHolder implements ToroPlayer {
        @BindView(R.id.profile_photo)
        CircleImageView profilePhoto;
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.ivEllipses)
        ImageView ivEllipses;
        @BindView(R.id.relLayout1)
        RelativeLayout relLayout1;
        @BindView(R.id.imageListView)
        Container imageListView;
        @BindView(R.id.image_frame)
        FrameLayout imageFrame;
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
        @BindView(R.id.pageIndicator)
        PageIndicator pageIndicator;
        private final SnapHelper snapHelper = new PagerSnapHelper();
        private int initPosition = -1;
        private ImageOrVideoAdapter mImageViewAdapter;

        HomeFeedViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            mImageViewAdapter = new ImageOrVideoAdapter(new ArrayList<>());

            imageListView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
//            imageListView.setPlayerSelector(PlayerSelector.DEFAULT);
//            imageListView.setCacheManager(CacheManager.DEFAULT);
            imageListView.setAdapter(mImageViewAdapter);

//            if (imageListView.getCacheManager() == null) {
//                imageListView.setCacheManager(new StateManager(mediaList));
//            }

//            snapHelper.attachToRecyclerView(imageListView);

            pageIndicator.attachTo(imageListView);

        }

        void onDetached() {
            snapHelper.attachToRecyclerView(null);
        }

        void onAttached() {
            snapHelper.attachToRecyclerView(imageListView);
        }

        public void bind(UserFeed.Feed feed) {


            ImageLoader.loadImage(itemView.getContext(), profilePhoto, feed.getPhoto());

            profilePhoto.setOnClickListener(view -> AnotherUserDetailsActivity.startUserActivity(view.getContext(),
                    String.valueOf(feed.getUserId()), feed.getFullname()
            ));

            String des = !TextUtils.isEmpty(feed.getDescription()) ? feed.getDescription() : "";
            imageCaption.setText(des);

            username.setText(feed.getFullname());

            imageLikes.setText(String.format("%s likes", feed.getLikeCount()));

            String comments = feed.getCommentCount() > 1 ? "comments" : "comment";

            imageCommentsLink.setText(String.format("View %s %s", feed.getCommentCount(), comments));

            List<UserFeed.Feed.Meta> list = feed.getMeta();

            if (list != null && list.size() > 0) {
                mImageViewAdapter.update(list);
                if (imageListView.getCacheManager() == null) {
                    imageListView.setCacheManager(new SimpleExoPlayerViewHolder.StateManager(list));
                }
            }

            int count = mImageViewAdapter.getItemCount();
            pageIndicator.setCount(count);
            pageIndicator.setVisibility(count > 1 ? View.VISIBLE : View.GONE);


        }

        @NonNull
        @Override
        public View getPlayerView() {
            return imageListView;
        }

        @NonNull
        @Override
        public PlaybackInfo getCurrentPlaybackInfo() {
            SparseArray<PlaybackInfo> actualInfos = imageListView.getLatestPlaybackInfos();
            ExtraPlaybackInfo resultInfo = new ExtraPlaybackInfo(actualInfos);

            List<ToroPlayer> activePlayers = imageListView.filterBy(Container.Filter.PLAYING);
            if (activePlayers.size() >= 1) {
                resultInfo.setResumeWindow(activePlayers.get(0).getPlayerOrder());
            }

            return resultInfo;
        }

        @Override
        public void initialize(@NonNull Container container, @NonNull PlaybackInfo playbackInfo) {
            this.initPosition = -1;
            if (playbackInfo instanceof ExtraPlaybackInfo) {
                //noinspection unchecked
                SparseArray<PlaybackInfo> cache = ((ExtraPlaybackInfo) playbackInfo).actualInfo;
                if (cache != null && cache.size() > 0) {
                    for (int i = 0; i < cache.size(); i++) {
                        int key = cache.keyAt(i);
                        this.imageListView.savePlaybackInfo(key, cache.get(key));
                    }
                }
                this.initPosition = playbackInfo.getResumeWindow();
            }
            this.imageListView.setPlayerSelector(PlayerSelector.NONE);
        }

        @Override
        public void play() {
            if (initPosition >= 0) this.imageListView.scrollToPosition(initPosition);
            initPosition = -1;
            this.imageListView.setPlayerSelector(PlayerSelector.DEFAULT);
        }

        @Override
        public void pause() {
            this.imageListView.setPlayerSelector(PlayerSelector.NONE);
        }

        @Override
        public boolean isPlaying() {
            return this.imageListView.filterBy(Container.Filter.PLAYING).size() > 0;
        }

        @Override
        public void release() {
// release here
            List<ToroPlayer> managed = this.imageListView.filterBy(Container.Filter.MANAGING);
            for (ToroPlayer player : managed) {
                if (player.isPlaying()) {
                    this.imageListView.savePlaybackInfo(player.getPlayerOrder(), player.getCurrentPlaybackInfo());
                    player.pause();
                }
                player.release();
            }
            this.imageListView.setPlayerSelector(PlayerSelector.NONE);
        }

        @Override
        public boolean wantsToPlay() {
            return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
        }

        @Override
        public int getPlayerOrder() {
            return getAdapterPosition();
        }
    }


    public static class SimpleExoPlayerViewHolder extends RecyclerView.ViewHolder implements ToroPlayer {

        static final int LAYOUT_RES = R.layout.vh_exoplayer_basic;

        @Nullable
        ExoPlayerViewHelper helper;
        EventListener listener;
        @Nullable
        private Uri mediaUri;

        @BindView(R.id.profile_photo)
        CircleImageView profilePhoto;
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.ivEllipses)
        ImageView ivEllipses;
        @BindView(R.id.relLayout1)
        RelativeLayout relLayout1;
        @BindView(R.id.image_frame)
        FrameLayout imageFrame;
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
        @BindView(R.id.pageIndicator)
        PageIndicator pageIndicator;

        @BindView(R.id.player)
        PlayerView playerView;
        @BindView(R.id.img)
        ImageView postImage;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        SimpleExoPlayerViewHolder(View itemView) {
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
        void bind(@NonNull RecyclerView.Adapter adapter, MyFeeds.Feed item, List<Object> payloads) {

            String des = !TextUtils.isEmpty(item.description) ? item.description : "";
            imageCaption.setText(des);

            MyFeeds.Feed.Image image = item.video;

            if (image != null) {
                mediaUri = Uri.parse(image.getVideo());
                postImage.layout(0, 0, 0, 0);
                Glide.with(postImage.getContext())
                        .load(image.getMedia())
                        .error(R.drawable.bg_img)
                        .placeholder(R.drawable.bg_img)
                        .apply(new RequestOptions().fitCenter())
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


        static class StateManager implements CacheManager {

            final List<UserFeed.Feed.Meta> mediaList;

            StateManager(List<UserFeed.Feed.Meta> mediaList) {
                this.mediaList = mediaList;
            }

            @NonNull
            @Override
            public Object getKeyForOrder(int order) {
                return this.mediaList.get(order);
            }

            @Nullable
            @Override
            public Integer getOrderForKey(@NonNull Object key) {
                return key instanceof MyFeeds.Feed.Image ? this.mediaList.indexOf(key) : null;
            }
        }
    }
}
