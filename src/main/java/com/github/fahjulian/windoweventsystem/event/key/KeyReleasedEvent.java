package com.github.fahjulian.windoweventsystem.event.key;

import com.github.fahjulian.windoweventsystem.event.Event;
import com.github.fahjulian.windoweventsystem.window.Keyboard;

public class KeyReleasedEvent extends Event {
    
    public final Keyboard.Key key;

    public KeyReleasedEvent(Keyboard.Key key) {
        this.key = key;
    }
}
