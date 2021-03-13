package com.instaclone.dashboard.messages;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.instaclone.R;
import com.instaclone.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessagingActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.dot_menu)
    ImageView dotMenu;
    @BindView(R.id.messageListView)
    RecyclerView mMessageListView;
    private MessageListAdapter mMessageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();

        }

        mMessageListView.setLayoutManager(new LinearLayoutManager(this));

        mMessageListAdapter = new MessageListAdapter(this, Utils.getMessageList());


        mMessageListView.setAdapter(mMessageListAdapter);

    }

    @OnClick({R.id.back, R.id.dot_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.dot_menu:
                break;
        }
    }
}
