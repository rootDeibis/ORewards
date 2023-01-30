package me.rootdeibis.orewards.common.cache;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Cache<T> {


    private Set<T> caches = new HashSet<T>();

    public Cache() {}

    public void add(T cache) {
        this.caches.add(cache);
    }

    public Set<T> all() {
        return this.caches;
    }

    public T find(Predicate<T> predication) {
        return this.caches.stream().filter(predication).findFirst().orElse(null);


    }

    public boolean has(Predicate<T> predication) {
        return this.find(predication) != null;
    }

    public void remove(Predicate<T> predication) {
        this.caches.removeIf(predication);
    }


}
