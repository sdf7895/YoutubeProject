package com.example.youtubeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.youtubeproject.CallBackInterface.SubActivityFinish;
import com.example.youtubeproject.Model.YoutubePlayerList;
import com.example.youtubeproject.View.YoutubeFragment;

import java.util.ArrayList;

public class SubActivity extends AppCompatActivity implements SubActivityFinish {
    ArrayList<YoutubePlayerList> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        if(getIntent() != null){
           lists = getIntent().getParcelableArrayListExtra("items");

        }

        YoutubeFragment youtubeFragment = new YoutubeFragment();
        youtubeFragment.setSelectLists(lists);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,youtubeFragment).commit();
    }

    public void SubActivityFinish(){
        finish();
    }

}
