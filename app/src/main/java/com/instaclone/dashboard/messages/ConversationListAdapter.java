package com.instaclone.dashboard.messages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.instaclone.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ViewHolder> {


    @NonNull
    @Override
    public ConversationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversations_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 12;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.photoImage)
//        ImageView photoImage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
