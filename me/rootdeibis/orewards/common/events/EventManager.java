package me.rootdeibis.orewards.common.events;

import me.rootdeibis.orewards.common.cache.Cache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventManager {

    private static final Cache<Listener> listeners = new Cache<>();

    public static void register(Listener listener) {
        listeners.add(listener);
    }

    public static void emit(Event event) {
        fire(event);
    }

    public static Cache<Listener> getListeners() {
        return listeners;
    }

    private static void fire(Event $event) {
        Set<Listener> $listeners = listeners.all();

        for (Listener $listener : $listeners) {
            List<Method> methods = Arrays.stream($listener.getClass().getMethods())
                    .filter(method -> Arrays.stream(method.getParameters())
                            .anyMatch(parameter -> parameter.getType().getName().equals($event.getClass().getName())))
                    .collect(Collectors.toList());

            for (Method method : methods) {
                try {
                    method.invoke($listener.getClass(), $event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
