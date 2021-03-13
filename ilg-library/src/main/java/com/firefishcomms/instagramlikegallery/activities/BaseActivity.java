package com.firefishcomms.instagramlikegallery.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firefishcomms.instagramlikegallery.R;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {

    protected RelativeLayout layout_loading_panel;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        toolbar = findViewById(R.id.include_toolbar);
        toolbar.findViewById(R.id.tv_toolbar_title).setVisibility(View.VISIBLE);

        if (toolbar != null) {
            try {
                setSupportActionBar(toolbar);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        ((TextView) toolbar.findViewById(R.id.tv_toolbar_title)).setText(getTitle());
    }


    /**
     * To show loading spinner
     * @param gravityPosition
     */
    protected void showLoadingSpinner(int gravityPosition){
        layout_loading_panel = findViewById(R.id.layout_loading_panel);
        if(layout_loading_panel != null){
            layout_loading_panel.setVisibility(View.VISIBLE);
            layout_loading_panel.setGravity(gravityPosition);
            layout_loading_panel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do nothing
                }
            });
        }
    }

    /**
     * To dismiss loading spinner
     */
    protected void dismissLoadingSpinner(){
        if(layout_loading_panel != null){
            layout_loading_panel.setVisibility(View.GONE);
        }
    }
}
