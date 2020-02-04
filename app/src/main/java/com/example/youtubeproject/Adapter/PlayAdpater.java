package com.example.youtubeproject.Adapter;

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

import java.util.ArrayList;

public class PlayAdpater extends RecyclerView.Adapter<PlayAdpater.ViewHolder> {
    private Context context;

    private ArrayList<YoutubePlayerList> playItems;

    private OnItemClickListener listener;
    private OnItemCancelListener cancelListener;

    private YoutubePlayerList youtubePlayerList;
    private YoutubePlayerList subYoutubePlayerList;

    public int savePosition = 0;
    private int switchValue = 0;

    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public static interface OnItemCancelListener {
        public void onCancelItemClick(ViewHolder holder, View view, int position);
    }

    public PlayAdpater(Context context, ArrayList<YoutubePlayerList> playItems) {
        this.context = context;
        this.playItems = playItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.music_items, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        youtubePlayerList = playItems.get(position);
        subYoutubePlayerList = playItems.get(position);

        viewHolder.title.setText(youtubePlayerList.title);
        viewHolder.dration.setText(youtubePlayerList.duration);

        selectedPosition(position, viewHolder);

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
        return playItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnCancelItemClickListener(OnItemCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView dration;
        private ImageView imageView;
        private OnItemClickListener listener;
        private OnItemCancelListener cancelListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView);
            dration = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.image);
            Button cancelButton = itemView.findViewById(R.id.calcelButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    toggleItemSelected(position);

                    savePosition = position;

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (cancelListener != null) {
                        cancelListener.onCancelItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public void setOnCancelItemClickListener(OnItemCancelListener cancelListener) {
            this.cancelListener = cancelListener;
        }
    }

    public void autoSelectedPosition(int position) {
        int realTimePosition = position == 0 ? playItems.size() - 1 : position - 1;

        savePosition = position;

        youtubePlayerList = playItems.get(position);
        subYoutubePlayerList = playItems.get(realTimePosition);

        if (youtubePlayerList.state) {

            youtubePlayerList.state = false;
            subYoutubePlayerList.state = true;

            notifyItemChanged(position);
            notifyItemChanged(realTimePosition);
        }
    }

    public void toggleItemSelected(int position) {
        position = position == playItems.size() ? 0 : position;
        savePosition = savePosition == playItems.size() ? 0 : savePosition;

        youtubePlayerList = playItems.get(position);
        subYoutubePlayerList = playItems.get(savePosition);

        if (youtubePlayerList.isState() && !subYoutubePlayerList.isState()) {
            youtubePlayerList.state = false;
            subYoutubePlayerList.state = true;

            notifyItemChanged(position);
            notifyItemChanged(savePosition);
        } else if (subYoutubePlayerList.isState()) {
            subYoutubePlayerList = playItems.get(savePosition);

            subYoutubePlayerList.state = true;
            youtubePlayerList.state = false;

            notifyItemChanged(position);
            notifyItemChanged(savePosition);
        }
    }

    private void selectedPosition(int position, ViewHolder viewHolder) {
        if (position != 0) {
            viewHolder.itemView.setSelected(youtubePlayerList.isState());
        } else {
            if (switchValue == 0) {

                switchValue = 1;
                youtubePlayerList.state = false;
                viewHolder.itemView.setSelected(youtubePlayerList.isState());

            } else {
                viewHolder.itemView.setSelected(youtubePlayerList.isState());
            }
        }
    }
}
