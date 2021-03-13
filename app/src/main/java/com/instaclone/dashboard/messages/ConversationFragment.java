package com.instaclone.dashboard.messages;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.instaclone.R;
import com.instaclone.dashboard.followerandfollowing.FollowerListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends Fragment {


    @BindView(R.id.searchView)
    EditText searchView;
    @BindView(R.id.conversationListView)
    RecyclerView mConversationListView;

    private ConversationListAdapter mConversationListAdapter;

    public ConversationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        ButterKnife.bind(this, view);

        mConversationListAdapter = new ConversationListAdapter();

        mConversationListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mConversationListView.setAdapter(mConversationListAdapter);

        return view;

    }

}
