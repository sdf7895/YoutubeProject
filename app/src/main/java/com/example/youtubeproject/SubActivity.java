package com.example.youtubeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.youtubeproject.CallBackInterface.SubActivityFinish;
import com.example.youtubeproject.View.YoutubeFragment;

public class SubActivity extends AppCompatActivity implements SubActivityFinish {

    int[] getPosition = new int[100];
    int[] getState = new int[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        if(getIntent() != null){
           Intent intent = getIntent();
           getPosition = intent.getExtras().getIntArray("position");
           getState = intent.getExtras().getIntArray("state");

        }

        YoutubeFragment youtubeFragment = new YoutubeFragment();
        youtubeFragment.positionANDstate(getPosition,getState);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,youtubeFragment).commit();
    }

    public void SubActivityFinish(){
        finish();
    }

}
