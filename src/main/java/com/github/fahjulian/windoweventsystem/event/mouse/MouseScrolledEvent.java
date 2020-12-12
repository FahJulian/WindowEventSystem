package com.github.fahjulian.windoweventsystem.event.mouse;

import com.github.fahjulian.windoweventsystem.event.Event;

public class MouseScrolledEvent extends Event {
    
    public final float scrollX;
    public final float scrollY;
    public final float posX;
    public final float posY;

    public MouseScrolledEvent(float scrollX, float scrollY, float posX, float posY) {
        this.scrollX = scrollX;
        this.scrollY = scrollY;
        this.posX = posX;
        this.posY = posY;
    }
}
