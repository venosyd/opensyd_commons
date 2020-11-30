package com.venosyd.open.commons.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.venosyd.open.entities.infra.SerializableEntity;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public enum CommonPool {

    INSTANCE;

    /**  */
    private Map<Class<?>, Map<Integer, Object>> _pool;

    CommonPool() {
        _pool = Collections.synchronizedMap(new HashMap<>());
    }

    /** limpar cache */
    public <T extends SerializableEntity> void clear(Class<T> clazz) {
        _pool.remove(clazz);
    }

    /** empurra o objeto na common pool */
    public <T extends SerializableEntity> void push(Class<T> clazz, T object) {
        Map<Integer, Object> cache = _pool.get(clazz);
        if (cache == null) {
            cache = Collections.synchronizedMap(new HashMap<>());
            _pool.put(clazz, cache);
        }

        cache.put(object.hashCode(), object);
    }

    /** retorna um objeto pela ID */
    @SuppressWarnings("unchecked")
    public <T extends SerializableEntity> T pull(Class<T> clazz, String id) {
        Map<Integer, Object> cache = _pool.get(clazz);
        if (cache == null) {
            cache = Collections.synchronizedMap(new HashMap<>());
            _pool.put(clazz, cache);
        }

        return (T) cache.get(Objects.hash(id));
    }

    /** retorna um objeto pela ID */
    @SuppressWarnings("unchecked")
    public <T extends SerializableEntity> List<T> list(Class<T> clazz) {
        Map<Integer, Object> cache = _pool.get(clazz);
        if (cache == null) {
            cache = Collections.synchronizedMap(new HashMap<>());
            _pool.put(clazz, cache);
        }

        return new LinkedList<T>((Collection<T>) (Collection<?>) cache.values());
    }

    /** retorna um objeto pela ID */
    @SuppressWarnings("unchecked")
    public <T extends SerializableEntity> T pop(Class<T> clazz, String id) {
        Map<Integer, Object> cache = _pool.get(clazz);
        if (cache == null) {
            cache = Collections.synchronizedMap(new HashMap<>());
            _pool.put(clazz, cache);
        }

        return (T) cache.remove(Objects.hash(id));
    }
}