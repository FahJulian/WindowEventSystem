package com.github.fahjulian.windoweventsystem.event.mouse;

import com.github.fahjulian.windoweventsystem.event.Event;
import com.github.fahjulian.windoweventsystem.window.Mouse;

public class MouseButtonReleasedEvent extends Event {
    
    public final Mouse.Button button;
    public final float posX;
    public final float posY;

    public MouseButtonReleasedEvent(Mouse.Button button, float posX, float posY) {
        this.button = button;
        this.posX = posX;
        this.posY = posY;
    }
}
