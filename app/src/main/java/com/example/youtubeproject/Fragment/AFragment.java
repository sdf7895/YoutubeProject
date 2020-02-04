package com.example.youtubeproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.youtubeproject.Activity.MainActivity;
import com.example.youtubeproject.Activity.PlayActivity;
import com.example.youtubeproject.Adapter.AAdapter;
import com.example.youtubeproject.DataBase.MySQLiteOpenHelper;
import com.example.youtubeproject.Model.YoutubePlayerList;
import com.example.youtubeproject.R;
import com.example.youtubeproject.app.CommonUtility;

import java.util.ArrayList;

public class AFragment extends Fragment {

    private Button playButton;
    private Button allSelectButton;

    private ArrayList<YoutubePlayerList> setItems;
    private ArrayList<YoutubePlayerList> videoLists;

    private AAdapter aadapter;

    private boolean selectAllState = true;

    @Override
    public void onResume() {
        super.onResume();

        selectAllState = true;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_a, container, false);

        videoLists = MySQLiteOpenHelper.videosSelect();

        playButton = rootView.findViewById(R.id.playButton);
        allSelectButton = rootView.findViewById(R.id.allButton);

        aadapter = new AAdapter(getContext(), videoLists);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        setItems = aadapter.getSelectItems();

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(aadapter);

        playButton();
        setAllSelectButton();

        return rootView;
    }

    private void playButton() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setItems.size() != 0) {
                    Intent intent = new Intent(getContext(), PlayActivity.class);
                    intent.putParcelableArrayListExtra("items", setItems);

                    startActivity(intent);
                    aadapter.clearSelectedItem();
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
                    aadapter.allSelectedItem();
                    selectAllState = false;
                } else {
                    aadapter.clearSelectedItem();
                    selectAllState = true;
                }
            }
        });
    }
}
