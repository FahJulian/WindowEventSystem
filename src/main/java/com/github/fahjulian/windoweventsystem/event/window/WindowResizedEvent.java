package com.github.fahjulian.windoweventsystem.event.window;

import com.github.fahjulian.windoweventsystem.event.Event;

public class WindowResizedEvent extends Event {
    
    public final float width;
    public final float height;

    public WindowResizedEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
