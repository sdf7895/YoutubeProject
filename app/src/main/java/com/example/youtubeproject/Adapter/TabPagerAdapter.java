package com.example.youtubeproject.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.youtubeproject.Fragment.AFragment;
import com.example.youtubeproject.Fragment.BFramgent;
import com.example.youtubeproject.Fragment.FavoritesFragment;
import com.example.youtubeproject.Fragment.SelectFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private SelectFragment selectFragment;
    private FavoritesFragment favoritesFragment;
    private AFragment aFragment;
    private BFramgent bFramgent;

    public TabPagerAdapter(FragmentManager fm, int tabCount, SelectFragment selectFragment, FavoritesFragment favoritesFragment,
                           AFragment aFragment, BFramgent bFramgent) {
        super(fm);
        this.tabCount = tabCount;
        this.selectFragment = selectFragment;
        this.favoritesFragment = favoritesFragment;
        this.aFragment = aFragment;
        this.bFramgent = bFramgent;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return selectFragment;
            case 1:
                return aFragment;
            case 2:
                return bFramgent;
            case 3:
                return favoritesFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;

    }


}

