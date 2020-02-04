package com.example.youtubeproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.youtubeproject.Activity.MainActivity;
import com.example.youtubeproject.Activity.PlayActivity;
import com.example.youtubeproject.Adapter.BAdapter;
import com.example.youtubeproject.DataBase.MySQLiteOpenHelper;
import com.example.youtubeproject.Model.YoutubePlayerList;
import com.example.youtubeproject.R;
import com.example.youtubeproject.app.CommonUtility;
import com.example.youtubeproject.app.EventBus;
import com.example.youtubeproject.app.OnFavoritesListener;
import com.squareup.otto.Subscribe;

import java.math.BigInteger;
import java.util.ArrayList;

public class BFramgent extends Fragment {

    private Button playButton;
    private Button allSelectButton;
    private BAdapter bAdapter;

    private ArrayList<YoutubePlayerList> setItems;
    private ArrayList<YoutubePlayerList> videoLists;
    private YoutubePlayerList youtubePlayerList;

    private boolean notice = true;
    private boolean selectAllState = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getInstance().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        selectAllState = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_b, container, false);

        videoLists = MySQLiteOpenHelper.videosSelect();

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        bAdapter = new BAdapter(getContext(),videoLists);

        setItems = bAdapter.getSelectItems();

        playButton = rootView.findViewById(R.id.playButton);
        allSelectButton = rootView.findViewById(R.id.allButton);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int gridPosition = position%3;

                switch (gridPosition){
                    case 0:
                        return 2;
                    case 1:
                    case 2:
                        return 1;
                }

                return 0;
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(bAdapter);

        playButton();
        setAllSelectButton();
        favoritesOnSelectListener();

        return rootView;
    }

    @Subscribe
    public void setFavoritesRelease(Boolean notice) {
        videoLists = MySQLiteOpenHelper.videosSelect();
        bAdapter.setSelectItems(videoLists);
    }

    private void favoritesOnSelectListener() {
        bAdapter.setOnItemFavoritesListener(new OnFavoritesListener() {
            @Override
            public void onFavoritesClick(RecyclerView.ViewHolder holder, View view, int position) {
                youtubePlayerList = videoLists.get(position);
                youtubePlayerList.favoritesState = !youtubePlayerList.isFavoritState();

                MySQLiteOpenHelper.favoritesUpdate(youtubePlayerList.url, youtubePlayerList.favoritesState);
                EventBus.getInstance().post(notice);
            }
        });
    }

    private void playButton() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setItems.size() != 0) {
                    Intent intent = new Intent(getContext(), PlayActivity.class);
                    intent.putParcelableArrayListExtra("items", setItems);

                    startActivity(intent);
                    bAdapter.clearSelectedItem();
                } else {
                    CommonUtility.showToast(getContext(), "동영상을 선택해주세요");
                }
            }
        });
    }

    private void setAllSelectButton() {
        allSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectAllState) {
                    bAdapter.allSelectedItem();
                    selectAllState = false;
                } else {
                    bAdapter.clearSelectedItem();
                    selectAllState = true;
                }
            }
        });
    }
}
