package com.example.youtubeproject.Model.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.youtubeproject.Model.YoutubePlayerList;
import com.example.youtubeproject.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    Context context;

    List<YoutubePlayerList> items;

    OnItemClickListener listener;
    OnItemCancelListener cancelListener;

    public static interface OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public static interface OnItemCancelListener{
        public void onCancelItemClick(ViewHolder holder, View view, int position);
    }

    public RecyclerViewAdapter(Context context,List<YoutubePlayerList> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.music_items,viewGroup,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
       YoutubePlayerList youtubePlayerList = items.get(position);
        viewHolder.title.setText(youtubePlayerList.title);
        viewHolder.dration.setText(youtubePlayerList.duration);

        Glide.with(context).asBitmap()
                .load(youtubePlayerList.url)
                .into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                viewHolder.imageView.setImageBitmap(resource);
            }
        });

        viewHolder.setOnItemClickListener(listener);
        viewHolder.setOnCancelItemClickListener(cancelListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setOnCancelItemClickListener(OnItemCancelListener cancelListener){
        this.cancelListener = cancelListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView dration;
        ImageView imageView;
        Button cancelButton;
        OnItemClickListener listener;
        OnItemCancelListener cancelListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView);
            dration = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.image);
            cancelButton = itemView.findViewById(R.id.calcelButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,v,position);
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(cancelListener != null){
                        cancelListener.onCancelItemClick(ViewHolder.this,v,position);
                    }
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        public void setOnCancelItemClickListener(OnItemCancelListener cancelListener){
            this.cancelListener = cancelListener;
        }
    }
}
