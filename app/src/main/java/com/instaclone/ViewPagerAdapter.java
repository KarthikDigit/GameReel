package com.instaclone;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private List<Integer> iconList = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
        notifyDataSetChanged();
    }

    public void addFragment(Fragment fragment, String title, int icon) {
        fragmentList.add(fragment);
        titleList.add(title);
        iconList.add(icon);
        notifyDataSetChanged();
    }


    public void addAllFragment(List<Fragment> fragmentList, List<String> titleList) {
        fragmentList.addAll(fragmentList);
        titleList.addAll(titleList);
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }


    public String getTitle(int position) {

        return titleList.get(position);
    }

    public Integer getIcon(int position) {

        return iconList.get(position);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
