package com.example.youtubeproject.app;

import com.squareup.otto.Bus;

public class EventBus extends Bus {
    private static final EventBus BUS = new EventBus();

    public static EventBus getInstance(){

        return BUS;
    }
}
