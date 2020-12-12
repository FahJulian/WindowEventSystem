package com.github.fahjulian.windoweventsystem.event.key;

import com.github.fahjulian.windoweventsystem.event.Event;
import com.github.fahjulian.windoweventsystem.window.Keyboard;

public class KeyPressedEvent extends Event {

    public final Keyboard.Key key;

    public KeyPressedEvent(Keyboard.Key key) {
        this.key = key;
    }
}
