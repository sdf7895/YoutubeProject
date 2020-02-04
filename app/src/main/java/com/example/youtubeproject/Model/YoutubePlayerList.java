package com.example.youtubeproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class YoutubePlayerList implements Parcelable {
    public int viewtype;
    public String title;
    public String url;
    public String id;
    public String duration;
    public boolean state;
    public boolean favoritesState;

    public YoutubePlayerList(int viewtype, String title, String url, String id, String duration,boolean state,boolean favoritesState) {
        this.viewtype = viewtype;
        this.title = title;
        this.url = url;
        this.id = id;
        this.duration = duration;
        this.state = state;
        this.favoritesState = favoritesState;
    }

    private YoutubePlayerList(Parcel in) {
        viewtype = in.readInt();
        title = in.readString();
        url = in.readString();
        id = in.readString();
        duration = in.readString();
        state = in.readByte() != 0;
        favoritesState = in.readByte() != 0;
    }

    public static final Creator<YoutubePlayerList> CREATOR = new Creator<YoutubePlayerList>() {
        @Override
        public YoutubePlayerList createFromParcel(Parcel in) {
            return new YoutubePlayerList(in);
        }

        @Override
        public YoutubePlayerList[] newArray(int size) {
            return new YoutubePlayerList[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(viewtype);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(id);
        dest.writeString(duration);
        dest.writeByte((byte) (state ? 1 : 0));
        dest.writeByte((byte) (favoritesState ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //--------------------------------------SET-------------------------------------------

    public void setViewtype() {this.viewtype = viewtype;}
    public void setTitle(String title) {this.title = title;}
    public void setDuration(String duration) {this.duration = duration;}
    public void setUrl(String url) {this.url = url;}
    public void setId(String id) {this.id = id;}
    public void setState(boolean state) {this.state = state;}
    public void setFavoritState(boolean favoritState) {this.favoritesState = favoritState;}

    //--------------------------------------GET-------------------------------------------

    public int getViewtype() {return viewtype;}
    public String getTitle() {return title;}
    public String getDuration() {return duration;}
    public String getUrl() {return url;}
    public String getId() {return id;}
    public boolean isState() {return state;}
    public boolean isFavoritState() {return favoritesState;}
}
