package com.example.youtubeproject.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.example.youtubeproject.app.ViewType;

import java.util.ArrayList;

public class AAdapter extends RecyclerView.Adapter<AAdapter.ViewHolder> {
    private Context context;
    private ArrayList<YoutubePlayerList> aItems;
    private ArrayList<YoutubePlayerList> aaItems;

    private YoutubePlayerList youtubePlayerList;

    public AAdapter(Context context, ArrayList<YoutubePlayerList> aItems) {
        this.context = context;
        this.aItems = aItems;

        aaItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.a_items, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        youtubePlayerList = aItems.get(position);
        viewHolder.title.setText(youtubePlayerList.getTitle());
        viewHolder.dration.setText(youtubePlayerList.getDuration());
        viewHolder.itemView.setSelected(youtubePlayerList.isState());

        Glide.with(context).asBitmap()
                .load(youtubePlayerList.url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        viewHolder.imageView.setImageBitmap(resource);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return aItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView dration;
        ImageView imageView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            dration = itemView.findViewById(R.id.duration);
            imageView = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleItemSelected(getAdapterPosition());
                }
            });

        }
    }

    private void toggleItemSelected(int position) {
        youtubePlayerList = aItems.get(position);
        int[] statePosition = new int[aItems.size()];

        if (!youtubePlayerList.isState()) {
            youtubePlayerList.state = true;

            aaItems.add(new YoutubePlayerList(ViewType.B_TYPE,youtubePlayerList.getTitle(),
                    youtubePlayerList.getUrl(),
                    youtubePlayerList.getId(),
                    youtubePlayerList.getDuration(),
                    youtubePlayerList.isState(),
                    youtubePlayerList.isFavoritState()));

            if(aaItems.size() == 1){
                statePosition[position] = 0;
            }else{
                statePosition[position] += 1;
            }

            notifyItemChanged(position);
        }else{
            youtubePlayerList.state = false;

            aaItems.remove(statePosition[position]);
            notifyItemChanged(position);
        }
    }

    public void clearSelectedItem() {
        for (int position = 0; position < aItems.size(); position++) {
            youtubePlayerList = aItems.get(position);
            if(youtubePlayerList.isState()){
                toggleItemSelected(position);
            }
        }
    }

    public void allSelectedItem() {
        for (int position = 0; position < aItems.size(); position++) {
            youtubePlayerList = aItems.get(position);
            if(!youtubePlayerList.isState()){
                toggleItemSelected(position);
            }
        }
    }

    public ArrayList<YoutubePlayerList> getSelectItems(){
        return aaItems;
    }
}
