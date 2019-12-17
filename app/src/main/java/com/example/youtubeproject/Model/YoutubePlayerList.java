package com.example.youtubeproject.Model;

public class YoutubePlayerList {

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
}
