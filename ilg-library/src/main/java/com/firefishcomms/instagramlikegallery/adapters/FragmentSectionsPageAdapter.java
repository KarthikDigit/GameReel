package com.firefishcomms.instagramlikegallery.adapters;



import com.firefishcomms.instagramlikegallery.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Anton on 6/7/2018.
 */

public class FragmentSectionsPageAdapter extends FragmentPagerAdapter {

    private final List<BaseFragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public void addFragment(BaseFragment fragment, String title){
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    public FragmentSectionsPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return fragmentTitleList.get(position);
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
