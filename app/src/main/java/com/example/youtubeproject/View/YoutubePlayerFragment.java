package com.example.youtubeproject.View;

import android.os.Bundle;

import com.example.youtubeproject.Model.YoutubePlayerList;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.List;

public class YoutubePlayerFragment extends YouTubePlayerSupportFragment {

    final String API_KEY = "AIzaSyBEIkW3ABdYf10EX50PBQBi4uU4G5bXhcM";

    YouTubePlayer.PlayerStateChangeListener stateListener;
    YouTubePlayer.PlaybackEventListener backEventListener;
    YoutubePlayerList youtubePlayerList;

    int position;

    int j = 1;

    public static YoutubePlayerFragment newInstance(String id,int position,int state,List<YoutubePlayerList> lists) {
        YoutubePlayerFragment youtubePlayerFragment = new YoutubePlayerFragment();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("position",position);
        bundle.putInt("state",state);

       youtubePlayerFragment.setArguments(bundle);
       youtubePlayerFragment.init(lists);

        return youtubePlayerFragment;
    }

    private void init(final List<YoutubePlayerList> lists) {
        initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                if (getArguments() != null) {
                    Bundle bundle = getArguments();
                    String id = bundle.getString("id");
                    position = bundle.getInt("position");
                    int state = bundle.getInt("state");


                    PlayStateListener(youTubePlayer, lists);
                    BackEvent();

                    youTubePlayer.setPlaybackEventListener(backEventListener);
                    youTubePlayer.setPlayerStateChangeListener(stateListener);
                    if(state == 0) {
                        startVideo(youTubePlayer,id);
                    }else if(state == 1) {
                        selectRemove(youTubePlayer,lists,position);
                    }
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    private void PlayStateListener(final YouTubePlayer youTubePlayer,final List<YoutubePlayerList> lists) {
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

                if(position == lists.size()-1 || j >= lists.size()) { //마지막동영상선택 혹은 동영상을 끝까지 돌았을경우 처음부터 다시 재생할수있는 기능
                    firstStart(lists, youTubePlayer);
                }else if (j < lists.size()) { //선택 동영상재생 혹은 순차적 동영상재생후 다끝나면 그다음 동영상이 실행되는 기능
                    nextStart(lists, youTubePlayer);
                }
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        };
    }

    private void BackEvent() {
        backEventListener = new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {

            }

            @Override
            public void onPaused() {

            }

            @Override
            public void onStopped() {

            }

            @Override
            public void onBuffering(boolean b) {

            }

            @Override
            public void onSeekTo(int MaxMillis) {

            }
        };
    }

    private void firstStart(List<YoutubePlayerList> lists,YouTubePlayer youTubePlayer){ //전체적으로 동영상들이 돌아가는기능
        youtubePlayerList = lists.get(0);
        youTubePlayer.loadVideo(youtubePlayerList.getId());

        position = -1;
        j =1;
    }

    private void nextStart(List<YoutubePlayerList> lists,YouTubePlayer youTubePlayer){ //순차적으로 동영상들이 돌아가는기능
        youtubePlayerList = lists.get(j);

        if(position >= 0){
            j = position +1;
            youtubePlayerList = lists.get(j);
            youTubePlayer.loadVideo(youtubePlayerList.getId());
        }else {
            youTubePlayer.loadVideo(youtubePlayerList.getId());
        }

        position = -1;
        j++;
    }

    private void startVideo(YouTubePlayer youTubePlayer,String id){ //처음 동영상을 띄워주는 기능
        youTubePlayer.loadVideo(id);
    }

    private void selectRemove(YouTubePlayer youTubePlayer,List<YoutubePlayerList> lists,int position){
        // 선택삭제를하게되면 동영상이 멈추고 그다음 동영상으로 넘어가는 기능

        if(position == lists.size()){
            youtubePlayerList = lists.get(0);
            youTubePlayer.loadVideo(youtubePlayerList.getId());
        }
        else{
            youtubePlayerList = lists.get(position);
            youTubePlayer.pause();
            youTubePlayer.loadVideo(youtubePlayerList.getId());
        }
    }

}

