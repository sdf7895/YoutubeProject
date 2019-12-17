package com.example.youtubeproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class YoutubePlayerList implements Parcelable {

    public String title;
    public String url;
    public String id;
    public String duration;
    public boolean state;

    public YoutubePlayerList(String title, String url, String id, String duration,boolean state) {
        this.title = title;
        this.url = url;
        this.id = id;
        this.duration = duration;
        this.state = state;
    }


    protected YoutubePlayerList(Parcel in) {
        title = in.readString();
        url = in.readString();
        id = in.readString();
        duration = in.readString();
        state = in.readByte() != 0;
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
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(id);
        dest.writeString(duration);
        dest.writeByte((byte) (state ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //----------------------SET----------------------
    public void setTitle(String title) {this.title = title;}
    public void setDuration(String duration) {this.duration = duration;}
    public void setUrl(String url) {this.url = url;}
    public void setId(String id) {this.id = id;}
    public void setState(boolean state) {this.state = state;}

    //----------------------GET----------------------
    public String getTitle() {return title;}
    public String getDuration() {return duration;}
    public String getUrl() {return url;}
    public String getId() {return id;}
    public boolean isState() {return state;}

} //보낼땐 putExtra 받을땐 getParcelableExtra
