package com.instaclone.dashboard.followerandfollowing;


import android.os.Bundle;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {

    @BindView(R.id.searchView)
    EditText searchView;
    @BindView(R.id.followerView)
    RecyclerView followerView;
    private FollowerListAdapter mFollowerListAdapter;

    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        ButterKnife.bind(this, view);

        mFollowerListAdapter = new FollowerListAdapter();

        followerView.setLayoutManager(new LinearLayoutManager(getContext()));
        followerView.setAdapter(mFollowerListAdapter);

        return view;
    }

}
