package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class viewpager extends FragmentPagerAdapter {
    private final List<Fragment> mList = new ArrayList<>();
    private final List<String> mTitleList = new ArrayList<>();

    public viewpager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0)
        return mList.get(0);
        else if(i==1)
            return mList.get(2);
        else if(i==2)
            return mList.get(1);
        return null;
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    public void addFragment(Fragment fragment, String title) {
        mList.add(fragment);
        mTitleList.add(title);
    }
}
