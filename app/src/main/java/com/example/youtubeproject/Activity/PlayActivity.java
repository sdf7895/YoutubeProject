package com.example.youtubeproject.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.youtubeproject.Adapter.PlayAdpater;
import com.example.youtubeproject.Model.YoutubePlayerList;
import com.example.youtubeproject.R;
import com.example.youtubeproject.app.CommonUtility;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    final String API_KEY = "AIzaSyBEIkW3ABdYf10EX50PBQBi4uU4G5bXhcM";

    private YouTubePlayer.PlayerStateChangeListener stateListener;

    private ArrayList<YoutubePlayerList> lists;
    private YoutubePlayerList youtubePlayerList;

    private PlayAdpater playAdpater;

    private YouTubePlayer youTubePlayer;

    private ImageButton allStateButton;

    private boolean statePlay = false;

    public int nextPlay = 0;
    public int sequential = -1;

    private int sequentialORrepeat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        if (getIntent() != null) {
            lists = getIntent().getParcelableArrayListExtra("items");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        allStateButton = findViewById(R.id.vedioStateButton);

        playAdpater = new PlayAdpater(getApplicationContext(), lists);

        toolbar.setTitle(R.string.youtube_play);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //백버튼 생성 코드

        recyclerViewSetOnItemClickListener();
        recyclerViewSetOnCancelItemClickListener();

        sequentialOrallPlay();

        YouTubePlayerSupportFragment youTubePlayerSupportFragment = (YouTubePlayerSupportFragment)getSupportFragmentManager()
                                                                    .findFragmentById(R.id.youtube_player_fragment);

        if(youTubePlayerSupportFragment != null) {
            youTubePlayerSupportFragment.initialize(API_KEY, this);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(playAdpater);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        this.youTubePlayer = youTubePlayer;
        youtubePlayerList = lists.get(0);

        youTubePlayer.loadVideo(youtubePlayerList.getId());

        playStateListener(youTubePlayer);
        youTubePlayer.setPlayerStateChangeListener(stateListener);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    //동영상 제어하는 메소드
    private void playStateListener(final YouTubePlayer youTubePlayer) {
        stateListener = new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded(String id) {

            }

            @Override
            public void onAdStarted() {

            }

            @Override
            public void onVideoStarted() {

            }

            @Override
            public void onVideoEnded() {
                if (sequentialORrepeat != 1) {
                    if (sequential == -1 && nextPlay == lists.size()-1) {
                        return;
                    }
                    repeat(youTubePlayer);
                } else {
                    repeat(youTubePlayer);
                }
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        };
    }

    private void selectRemove(int position) {
        if (position == lists.size()) {
            youtubePlayerList = lists.get(0);
            youTubePlayer.loadVideo(youtubePlayerList.getId());
        } else {
            youTubePlayer.pause();
            youtubePlayerList = lists.get(position);
            youTubePlayer.loadVideo(youtubePlayerList.getId());
        }
    }

    private void repeat(YouTubePlayer youTubePlayer) {
        nextPlay = nextPlay < lists.size() - 1 ? ++nextPlay : 0;

        youtubePlayerList = lists.get(nextPlay);

        playAdpater.autoSelectedPosition(nextPlay);
        youTubePlayer.loadVideo(youtubePlayerList.getId());
    }

    private void recyclerViewSetOnItemClickListener() {
        playAdpater.setOnItemClickListener(new PlayAdpater.OnItemClickListener() {
            @Override
            public void onItemClick(PlayAdpater.ViewHolder holder, View view, int position) {
                nextPlay = position;

                youtubePlayerList = lists.get(position);

                youTubePlayer.loadVideo(youtubePlayerList.getId());
            }
        });
    }

    private void recyclerViewSetOnCancelItemClickListener() {
        playAdpater.setOnCancelItemClickListener(new PlayAdpater.OnItemCancelListener() {
            @Override
            public void onCancelItemClick(PlayAdpater.ViewHolder holder, View view, int position) {
                if (position == -1) {
                    return;
                } else {
                    nextPlay = nextPlay == lists.size() ? 0 : nextPlay;
                    youtubePlayerList = lists.get(position);
                }

                if (lists.size() == 1) {
                    lists.remove(position);

                    finish();
                } else if (nextPlay == position) {
                    lists.remove(position);

                    playAdpater.toggleItemSelected(position);

                    selectRemove(position);
                } else if (nextPlay < position) {
                    lists.remove(position);

                } else {
                    nextPlay -= 1;
                    playAdpater.savePosition -= 1;

                    lists.remove(position);
                }
                playAdpater.notifyDataSetChanged();
            }
        });
    }

    private void sequentialOrallPlay() {
        allStateButton.setBackgroundResource(R.drawable.state_button);
        allStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (statePlay) {
                    allStateButton.setBackgroundResource(R.drawable.state_button);
                    sequentialORrepeat = 0;
                    sequential = -1;

                    CommonUtility.showToast(getApplicationContext(), "반복재생이 해제되었습니다");

                    statePlay = false;
                } else {
                    allStateButton.setBackgroundResource(R.drawable.select_state_button);
                    sequentialORrepeat = 1;
                    sequential = 0;

                    CommonUtility.showToast(getApplicationContext(), "반복재생이 선택되었습니다");

                    statePlay = true;
                }
            }
        });
    }
}
