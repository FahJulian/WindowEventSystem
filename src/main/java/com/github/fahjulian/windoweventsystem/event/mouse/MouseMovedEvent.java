package com.github.fahjulian.windoweventsystem.event.mouse;

import com.github.fahjulian.windoweventsystem.event.Event;

public class MouseMovedEvent extends Event {

    public final float posX;
    public final float posY;
    public final float deltaX;
    public final float deltaY;
    
    public MouseMovedEvent(float posX, float posY, float deltaX, float deltaY) {
        this.posX = posX;
        this.posY = posY;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
}
