package com.example.youtubeproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.youtubeproject.Activity.PlayActivity;
import com.example.youtubeproject.Adapter.FavoritesAdapter;
import com.example.youtubeproject.DataBase.MySQLiteOpenHelper;
import com.example.youtubeproject.Model.YoutubePlayerList;
import com.example.youtubeproject.R;
import com.example.youtubeproject.app.OnFavoritesListener;
import com.example.youtubeproject.app.EventBus;
import com.example.youtubeproject.app.CommonUtility;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    private Button playButton;
    private Button allSelectButton;

    private ArrayList<YoutubePlayerList> favoritesLists;
    private ArrayList<YoutubePlayerList> setItems;
    private YoutubePlayerList youtubePlayerList;
    private FavoritesAdapter favoritesAdapter;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_favorites_view, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        playButton = rootView.findViewById(R.id.playButton);
        allSelectButton = rootView.findViewById(R.id.allButton);

        favoritesLists = MySQLiteOpenHelper.favoritesSelect();

        favoritesAdapter = new FavoritesAdapter(getContext(), favoritesLists);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(favoritesAdapter);

        setItems = favoritesAdapter.getSelectItems();

        favoritesOnReleaseListener();
        playButton();
        setAllSelectButton();

        return rootView;
    }

    @Subscribe
    public void setFavoritesSelect(Boolean notice) {
        favoritesLists = MySQLiteOpenHelper.favoritesSelect();
        favoritesAdapter.setFavoritesItems(favoritesLists);
    }

    private void favoritesOnReleaseListener() {
        favoritesAdapter.setOnItemFavoritesListener(new OnFavoritesListener() {
            @Override
            public void onFavoritesClick(RecyclerView.ViewHolder holder, View view, int position) {
                youtubePlayerList = favoritesLists.get(position);
                youtubePlayerList.favoritesState = !youtubePlayerList.favoritesState;

                MySQLiteOpenHelper.favoritesUpdate(youtubePlayerList.url, youtubePlayerList.isFavoritState());
                EventBus.getInstance().post(notice);

                favoritesAdapter.notifyDataSetChanged();
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
                    favoritesAdapter.clearSelectedItem();
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
                    favoritesAdapter.allSelectedItem();
                    selectAllState = false;
                } else {
                    favoritesAdapter.clearSelectedItem();
                    selectAllState = true;
                }
            }
        });
    }
}


