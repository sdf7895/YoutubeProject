package com.example.youtubeproject.Model.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.youtubeproject.View.SelectView;
import com.example.youtubeproject.View.StarView;

public class TapPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private SelectView selectView = SelectView.newInstance();
    private StarView starView = StarView.newInstance();

    public TapPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                return selectView;
            case 1:

                return starView;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

