package com.example.youtubeproject.Model.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.youtubeproject.Model.YoutubePlayerList;
import com.example.youtubeproject.R;


import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<YoutubePlayerList> items;
    private OnItemClickListener listener;
    private YoutubePlayerList youtubePlayerList;

    int[] state = new int[100];
    int[] getPosition = new int[100];
    int[] getState = new int[100];

    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public MainRecyclerViewAdapter(Context context, List<YoutubePlayerList> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.mainmusic_items, viewGroup, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        youtubePlayerList = items.get(position);
        viewHolder.title.setText(youtubePlayerList.title);
        viewHolder.dration.setText(youtubePlayerList.duration);

        viewHolder.itemView.setSelected(youtubePlayerList.isState());

        Glide.with(context).asBitmap()
                .load(youtubePlayerList.url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        viewHolder.imageView.setImageBitmap(resource);
                    }
                });

        viewHolder.setOnItemClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView dration;
        ImageView imageView;
        OnItemClickListener listener;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView);
            dration = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    toggleItemSelected(position);

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }

    private void toggleItemSelected(int position) {
        youtubePlayerList = items.get(position);

        if (youtubePlayerList.isState() == false) {
            youtubePlayerList.state = true;
            notifyItemChanged(position);
        }else {
            youtubePlayerList.state = false;
            notifyItemChanged(position);
        }
    }

    public void clearSelectedItem() {
        for (int position = 0; position < items.size(); position++) {
            youtubePlayerList = items.get(position);
            if(youtubePlayerList.isState() == true){
                toggleItemSelected(position);
            }
        }
    }

    public void allSelectedItem() { //전체선택했을때의경우
        for (int position = 0; position < items.size(); position++) {
            youtubePlayerList = items.get(position);
            if(youtubePlayerList.isState() == false){
                toggleItemSelected(position);
            }
        }
    }

    public int[] getPosition(){
        int j =0;

        for(int i =0; i < items.size(); i++){
            if(state[i] == 1){
                getPosition[j] = i;
                j++;
            }
        }
        return getPosition;
    }

    public int[] getState(){
        int j =0;

        for(int i =0; i < items.size(); i++){
            if(state[i] == 1){
                getState[j] = 1;
                j++;
            }
        }
        return getState;
    }

    public void addDelete(){
        for(int i =0; i <items.size(); i++){
            state[i] =0;
            getPosition[i] = 0;
            getState[i] = 0;
            toggleItemSelected(i);
        }
    }

}
