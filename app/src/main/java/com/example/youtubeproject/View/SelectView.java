package com.example.youtubeproject.View;

import android.content.Context;
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
import android.widget.Toast;

import com.example.youtubeproject.CallBackInterface.GetFragmentInterface;
import com.example.youtubeproject.Model.Adapter.MainRecyclerViewAdapter;
import com.example.youtubeproject.Model.YoutubePlayerList;
import com.example.youtubeproject.R;
import com.example.youtubeproject.Model.YoutubeSetData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class SelectView extends Fragment {
    Button playButton;
    Button allSelectButton;
    MainRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<YoutubePlayerList> setItems;

    GetFragmentInterface callBack;

    private boolean mState = true;


    public static SelectView newInstance(){
        SelectView selectView = new SelectView();

        return selectView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof GetFragmentInterface) { //메인액티비티를 인터페이스 타입을 참조하는지 확인하는 코드
            callBack = (GetFragmentInterface) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBack = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.select_view,container,false);

        YoutubeSetData youtubeSetData = new YoutubeSetData();
        ArrayList<YoutubePlayerList> lists = youtubeSetData.youtubeSetData();

        final RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        playButton = rootView.findViewById(R.id.playButton);
        allSelectButton = rootView.findViewById(R.id.allButton);
        recyclerViewAdapter = new MainRecyclerViewAdapter(getContext(),lists);

        setItems =  recyclerViewAdapter.getItems();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        playButton();
        setAllSelectButton();


        return rootView;
    }

    private void playButton(){

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setItems.size() != 0){
                    callBack.getSubActivity(setItems);
                    recyclerViewAdapter.clearSelectedItem();
                }else{
                    Toast.makeText(getContext(), "동영상을선택해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setAllSelectButton(){

        allSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mState == true) {
                    recyclerViewAdapter.allSelectedItem();

                    mState = false;
                }else{
                    recyclerViewAdapter.clearSelectedItem();

                    mState = true;
                }
            }
        });

    }
}
