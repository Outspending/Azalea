package me.outspending.events;

import me.outspending.events.types.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventExecutor {
    private static final Logger logger = LoggerFactory.getLogger(EventExecutor.class);
    private static final HashMap<Class<? extends Event>, List<Listener>> registeredListeners = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static void registerEvents(EventListener listener) {
        Class<? extends EventListener> clazz = listener.getClass();
        for (Method method : clazz.getMethods()) {
            if (!method.isAnnotationPresent(EventHandler.class)) {
                continue;
            }

            if (method.getParameterCount() != 1) {
                logger.error("Method {} in class {} has an invalid parameter count.", method.getName(), clazz.getName());
                continue;
            }

            if (!Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                logger.error("Method {} in class {} has an invalid parameter type.", method.getName(), clazz.getName());
                continue;
            }

            try {
                Class<? extends Event> eventType = (Class<? extends Event>) method.getParameterTypes()[0];
                addListener(eventType, new Listener(listener, method));
            } catch (Exception e) {
                logger.error("Failed to register event listener for method {} in class {}.", method.getName(), clazz.getName(), e);
            }
        }
    }

    private static void addListener(Class<? extends Event> eventType, Listener listener) {
        registeredListeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public static void emitEvent(Event event) {
        Class<? extends Event> eventClass = event.getClass();
        registeredListeners.getOrDefault(eventClass, new ArrayList<>()).forEach(listener -> {
            try {
                listener.listenerMethod.setAccessible(true);
                listener.listenerMethod.invoke(listener.declaringClass, event);
            } catch (Exception e) {
                logger.error("Failed to invoke event listener for event {}.", eventClass.getName(), e);
            }
        });
    }

    private static class Listener {
        private final Object declaringClass;
        private final Method listenerMethod;

        public Listener(Object declaringClass, Method listenerMethod) {
            this.declaringClass = declaringClass;
            this.listenerMethod = listenerMethod;
        }
    }
}

