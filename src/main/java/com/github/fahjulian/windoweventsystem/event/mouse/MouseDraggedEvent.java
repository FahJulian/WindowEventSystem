package com.github.fahjulian.windoweventsystem.event.mouse;

import com.github.fahjulian.windoweventsystem.event.Event;
import com.github.fahjulian.windoweventsystem.window.Mouse;

public class MouseDraggedEvent extends Event {

    public final Mouse.Button button;
    public final float posX;
    public final float posY;
    public final float deltaX;
    public final float deltaY;
    
    public MouseDraggedEvent(Mouse.Button button, float posX, float posY, float deltaX, float deltaY) {
        this.button = button;
        this.posX = posX;
        this.posY = posY;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
}
