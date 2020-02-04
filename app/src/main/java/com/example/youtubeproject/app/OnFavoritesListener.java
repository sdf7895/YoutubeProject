package com.example.youtubeproject.app;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface OnFavoritesListener {
    void onFavoritesClick(RecyclerView.ViewHolder holder, View view, int position);
}
