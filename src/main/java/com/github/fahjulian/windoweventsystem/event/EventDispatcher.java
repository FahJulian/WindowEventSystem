package com.github.fahjulian.windoweventsystem.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher<E extends Event> {
    
    private final List<EventListener<E>> listeners;
    private final Map<Object, Integer> clientIndices;

    public EventDispatcher() {
        listeners = new ArrayList<>();
        clientIndices = new HashMap<>();
    }

    public int add(EventListener<E> listener) {
        listeners.add(listener);
        return listeners.size() - 1;
    }

    public void add(Object client, EventListener<E> listener) {
        assert !clientIndices.containsKey(client) : "Can only add one listener for each client." + 
            "If you want to add more than one listner, you need to manage the listener IDs in the client";
        clientIndices.put(client, add(listener));
    }

    public void remove(int listenerID) {
        listeners.set(listenerID, null);
    }

    public void remove(Object client) {
        Integer idx = clientIndices.remove(client);
        if (idx != null) listeners.set(idx, null);
    }

    public void dispatch(E event) {
        for (int i = 0; i < listeners.size(); i++) {
            EventListener<E> listener = listeners.get(i);
            if (listener != null) listener.onEvent(event);
        }
    }
}
