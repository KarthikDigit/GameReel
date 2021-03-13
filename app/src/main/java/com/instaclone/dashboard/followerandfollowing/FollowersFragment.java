package com.instaclone.dashboard.followerandfollowing;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.instaclone.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersFragment extends Fragment {


    @BindView(R.id.searchView)
    EditText searchView;
    @BindView(R.id.followerView)
    RecyclerView followerView;

    private FollowerListAdapter mFollowerListAdapter;

    public FollowersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        ButterKnife.bind(this, view);

        mFollowerListAdapter = new FollowerListAdapter();

        followerView.setLayoutManager(new LinearLayoutManager(getContext()));
        followerView.setAdapter(mFollowerListAdapter);

        return view;
    }


}
