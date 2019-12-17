package com.example.youtubeproject.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.youtubeproject.CallBackInterface.SubActivityFinish;
import com.example.youtubeproject.Model.Adapter.RecyclerViewAdapter;
import com.example.youtubeproject.Model.YoutubePlayerList;
import com.example.youtubeproject.R;

import java.util.ArrayList;
import java.util.List;


public class YoutubeFragment extends Fragment{
    YoutubePlayerFragment youtubePlayerFragment;
    FragmentTransaction transaction;

    YoutubePlayerList youtubePlayerList;
    YoutubePlayerList youtubePlayerList1;

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;

    List<YoutubePlayerList> selectLists = new ArrayList<>();

    SubActivityFinish callback;

    int statePosition;
    int[] getPosition = new int[100];
    int[] getState = new int[100];

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof SubActivityFinish){
            callback = (SubActivityFinish)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.youtube_fragment,container,false);


        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(),selectLists);

        youtubePlayerList = selectLists.get(0);

        youtubePlayerFragment = YoutubePlayerFragment.newInstance(youtubePlayerList.getId(),0,0,selectLists);

        recyclerViewSetOnCancelItemClickListener();
        recyclerViewSetOnItemClickListener();

        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_layout,youtubePlayerFragment).commit();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        return rootView;
    }

    public void recyclerViewSetOnItemClickListener() {
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewAdapter.ViewHolder holder, View view, int position) {
                youtubePlayerList1 = selectLists.get(position);
                statePosition = position;

                setBeginTransaction(youtubePlayerList1,position,0,selectLists);
            }
        });


    }

    public void recyclerViewSetOnCancelItemClickListener() {
        recyclerViewAdapter.setOnCancelItemClickListener(new RecyclerViewAdapter.OnItemCancelListener() {
            @Override
            public void onCancelItemClick(RecyclerViewAdapter.ViewHolder holder, View view, int position) {
                youtubePlayerList1 = selectLists.get(position);

                if (position == 0 && selectLists.size() == 1) {
                    selectLists.remove(position);

                    callback.SubActivityFinish();


                } else if (statePosition == position) {
                    if(position == selectLists.size()-1){ //맨마지막 동영상 삭제시 상태값 0 으로 초기화해야 삭제만되고 동영상은 플레이 되는버그가안남
                        statePosition =0;
                    }
                    selectLists.remove(position);
                    setBeginTransaction(youtubePlayerList1,position,1,selectLists);

                }else{
                    if(statePosition<position){
                        selectLists.remove(position);
                    }else if(statePosition>position){
                        statePosition-=1;
                        selectLists.remove(position);
                    }
                }

                recyclerViewAdapter.notifyDataSetChanged();

            }
        });
    }

    public void positionANDstate(int[] getPosition,int[] getState){
        this.getPosition = getPosition;
        this.getState = getState;
    }

    public void setBeginTransaction(YoutubePlayerList youtubePlayerList,int position,int state,List<YoutubePlayerList> selectLists){

        youtubePlayerFragment = YoutubePlayerFragment.newInstance(youtubePlayerList.getId(),position,state,selectLists);

        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_layout,youtubePlayerFragment).commit();

    }

}
