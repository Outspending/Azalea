package me.outspending.events;

import lombok.Getter;
import me.outspending.events.types.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public class EventNode {
    private final Map<Class<? extends Event>, Consumer<? extends Event>> listeners = new HashMap<>();

    public <T extends Event> EventNode addListener(Class<T> eventClass, Consumer<T> consumer) {
        listeners.put(eventClass, consumer);
        return this;
    }
}
