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


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<YoutubePlayerList> favoritesItems;
    private ArrayList<YoutubePlayerList> setItems;

    private OnFavoritesListener favoritesListener;

    private YoutubePlayerList youtubePlayerList;

    public FavoritesAdapter(Context context, ArrayList<YoutubePlayerList> favoritesItems) {
        this.context = context;
        this.favoritesItems = favoritesItems;

        setItems = new ArrayList<>();
    }

    public void setFavoritesItems(ArrayList<YoutubePlayerList> favoritesItems){
        this.favoritesItems = favoritesItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.favorit_items, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        youtubePlayerList = favoritesItems.get(position);
        viewHolder.title.setText(youtubePlayerList.title);
        viewHolder.dration.setText(youtubePlayerList.duration);
        viewHolder.itemView.setSelected(youtubePlayerList.isState());
        viewHolder.favoritesButton.setSelected(youtubePlayerList.isFavoritState());

        Glide.with(context).asBitmap()
                .load(youtubePlayerList.url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        viewHolder.imageView.setImageBitmap(resource);
                    }
                });

        viewHolder.setOnItemFavoritesListener(favoritesListener);
    }

    @Override
    public int getItemCount() {
        return favoritesItems.size();
    }

    public void setOnItemFavoritesListener(OnFavoritesListener favoritesListener) {
        this.favoritesListener = favoritesListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView dration;
        ImageView imageView;
        ImageButton favoritesButton;
        OnFavoritesListener favoritesListener;

        private ViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView);
            dration = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.image);
            favoritesButton = itemView.findViewById(R.id.favorit_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    toggleItemSelected(position);
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

        private void setOnItemFavoritesListener(OnFavoritesListener favoritesListener) {
            this.favoritesListener = favoritesListener;
        }
    }

    private void toggleItemSelected(int position) {
        youtubePlayerList = favoritesItems.get(position);
        int[] statePosition = new int[favoritesItems.size()];

        if (!youtubePlayerList.isState()) {
            youtubePlayerList.state = true;
            setItems.add(new YoutubePlayerList(ViewType.B_TYPE,youtubePlayerList.getTitle(),
                    youtubePlayerList.getUrl(),
                    youtubePlayerList.getId(),
                    youtubePlayerList.getDuration(),
                    youtubePlayerList.isState(),
                    youtubePlayerList.isFavoritState()));

            if (setItems.size() == 1) {
                statePosition[position] = 0;
            } else {
                statePosition[position] += 1;
            }

            notifyItemChanged(position);
        } else {
            youtubePlayerList.state = false;

            setItems.remove(statePosition[position]);
            notifyItemChanged(position);
        }
    }

    public void clearSelectedItem() { //전체취소했을때
        for (int position = 0; position < favoritesItems.size(); position++) {
            youtubePlayerList = favoritesItems.get(position);
            if (youtubePlayerList.isState()) {
                toggleItemSelected(position);
            } else {
                toggleItemSelected(position);
            }
        }
    }

    public void allSelectedItem() { //전체선택했을때
        for (int position = 0; position < favoritesItems.size(); position++) {
            youtubePlayerList = favoritesItems.get(position);
            if (!youtubePlayerList.isState()) {
                toggleItemSelected(position);
            }
        }
    }

    public ArrayList<YoutubePlayerList> getSelectItems(){
        return setItems;
    }
}
