package com.example.youtubeproject.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.youtubeproject.Model.YoutubePlayerList;
import com.example.youtubeproject.R;
import com.example.youtubeproject.app.ViewType;
import com.example.youtubeproject.app.OnFavoritesListener;

import java.util.ArrayList;

public class BAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<YoutubePlayerList> bItems;
    private ArrayList<YoutubePlayerList> bbItems;
    private OnFavoritesListener favoritesListener;

    private YoutubePlayerList youtubePlayerList;

    public BAdapter(Context context, ArrayList<YoutubePlayerList> BItems) {
        this.context = context;
        this.bItems = BItems;

        this.bbItems = new ArrayList<>();
    }

    public void setSelectItems(ArrayList<YoutubePlayerList> selectItems){
        this.bItems = selectItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = viewType == ViewType.B_TYPE ? inflater.inflate(R.layout.b_items,viewGroup,false):
                                                      inflater.inflate(R.layout.bb_items,viewGroup,false);

            return viewType == ViewType.B_TYPE?new ViewHolder(view):new ViewHolder2(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int psotion) {
        youtubePlayerList = bItems.get(psotion);

        if(viewHolder instanceof ViewHolder){
            ((ViewHolder) viewHolder).title.setText(youtubePlayerList.getTitle());
            ((ViewHolder) viewHolder).dration.setText(youtubePlayerList.getDuration());
            ((ViewHolder) viewHolder).itemView.setSelected(youtubePlayerList.isState());
            ((ViewHolder) viewHolder).favoritesButton.setSelected(youtubePlayerList.isFavoritState());

            Glide.with(context).asBitmap()
                    .load(youtubePlayerList.url)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            ((ViewHolder) viewHolder).imageView.setImageBitmap(resource);
                        }
                    });

            ((ViewHolder) viewHolder).setOnItemFavoritesListener(favoritesListener);

        }else if(viewHolder instanceof ViewHolder2){
            ((ViewHolder2) viewHolder).title.setText(youtubePlayerList.getTitle());
            ((ViewHolder2) viewHolder).dration.setText(youtubePlayerList.getDuration());
            ((ViewHolder2) viewHolder).itemView.setSelected(youtubePlayerList.isState());
            ((ViewHolder2) viewHolder).favoritesButton.setSelected(youtubePlayerList.isFavoritState());

            Glide.with(context).asBitmap()
                    .load(youtubePlayerList.url)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            ((ViewHolder2) viewHolder).imageView.setImageBitmap(resource);
                        }
                    });

            ((ViewHolder2) viewHolder).setOnItemFavoritesListener(favoritesListener);
        }
    }

    @Override
    public int getItemCount() {
        return bItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return bItems.get(position).getViewtype();
    }

    public void setOnItemFavoritesListener(OnFavoritesListener favoritesListener){
        this.favoritesListener = favoritesListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView dration;
        ImageView imageView;
        ImageButton favoritesButton;
        OnFavoritesListener favoritesListener;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            dration = itemView.findViewById(R.id.duration);
            imageView = itemView.findViewById(R.id.image);
            favoritesButton = itemView.findViewById(R.id.favorit_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleItemSelected(getAdapterPosition());
                }
            });

            favoritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favoritesListener.onFavoritesClick(ViewHolder.this, v, getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }

        private void setOnItemFavoritesListener(OnFavoritesListener favoritesListener){
            this.favoritesListener = favoritesListener;
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView title;
        TextView dration;
        ImageView imageView;
        ImageButton favoritesButton;
        OnFavoritesListener favoritesListener;

        ViewHolder2(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            dration = itemView.findViewById(R.id.duration);
            imageView = itemView.findViewById(R.id.image);
            favoritesButton = itemView.findViewById(R.id.favorit_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleItemSelected(getAdapterPosition());
                }
            });

            favoritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favoritesListener.onFavoritesClick(ViewHolder2.this, v, getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }

        private void setOnItemFavoritesListener(OnFavoritesListener favoritesListener){
            this.favoritesListener = favoritesListener;
        }
    }

    private void toggleItemSelected(int position) {
        youtubePlayerList = bItems.get(position);
        int[] statePosition = new int[bItems.size()];

        if (!youtubePlayerList.isState()) {
            youtubePlayerList.state = true;

            bbItems.add(new YoutubePlayerList(ViewType.B_TYPE,youtubePlayerList.getTitle(),
                    youtubePlayerList.getUrl(),
                    youtubePlayerList.getId(),
                    youtubePlayerList.getDuration(),
                    youtubePlayerList.isState(),
                    youtubePlayerList.isFavoritState()));

            if(bbItems.size() == 1){
                statePosition[position] = 0;
            }else{
                statePosition[position] += 1;
            }

            notifyItemChanged(position);
        }else{
            youtubePlayerList.state = false;

            bbItems.remove(statePosition[position]);
            notifyItemChanged(position);
        }
    }

    public void clearSelectedItem() {
        for (int position = 0; position < bItems.size(); position++) {
            youtubePlayerList = bItems.get(position);
            if(youtubePlayerList.isState()){
                toggleItemSelected(position);
            }
        }
    }

    public void allSelectedItem() {
        for (int position = 0; position < bItems.size(); position++) {
            youtubePlayerList = bItems.get(position);
            if(!youtubePlayerList.isState()){
                toggleItemSelected(position);
            }
        }
    }

    public ArrayList<YoutubePlayerList> getSelectItems(){
        return bbItems;
    }
}
