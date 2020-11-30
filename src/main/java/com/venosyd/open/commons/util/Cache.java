package com.venosyd.open.commons.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.venosyd.open.entities.infra.SerializableEntity;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public enum Cache {

    INSTANCE;

    private Map<String, Object> _cache = new ConcurrentHashMap<>();

    /**
     * retorna dados por uma chave
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) _cache.get(key);
    }

    /**
     * insere novo dado no cache
     */
    public void set(String key, Object data) {
        _cache.put(key, data);
    }

    /**
     * cria um dicionario usando uma lista de dados (a chave eh o ID deles) e salva
     * no cache
     */
    public <T extends SerializableEntity> void setMap(String key, Collection<T> data) {
        var cache = new HashMap<Serializable, T>();
        data.forEach(d -> cache.put(d.getId(), d));

        set(key, cache);
    }

    /**
     * salva um item de um dicionario em cache
     */
    public <T extends SerializableEntity> void saveItem(String key, T data) {
        var cache = this.<Map<Serializable, T>>get(key);
        if (cache == null) {
            setMap(key, Arrays.asList(data));
            cache = get(key);
        }

        cache.put(data.getId(), data);
    }

    /**
     * salva um item de um dicionario em cache
     */
    public <T extends SerializableEntity> void saveItems(String key, Collection<T> data) {
        var cache = this.<Map<Serializable, T>>get(key);
        if (cache == null) {
            setMap(key, data);
            cache = get(key);
        }

        for (T item : data)
            cache.put(item.getId(), item);
    }

    /**
     * remove um item de um dicionario em cache
     */
    public <T extends SerializableEntity> void removeItem(String key, Serializable id) {
        var cache = this.<Map<Serializable, T>>get(key);
        if (cache != null) {
            cache.remove(id);
        }
    }

    /**
     * retorna o item especifico de um mapa cacheado
     */
    public <T extends SerializableEntity> T getItem(String key, Serializable id) {
        var cache = this.<Map<Serializable, T>>get(key);
        return cache == null ? null : cache.get(id);
    }

    /**
     * retorna todos os itens de um mapa cacheado
     */
    public <T extends SerializableEntity> List<T> listItems(String key) {
        var cache = this.<Map<Serializable, T>>get(key);
        return cache == null ? null : new ArrayList<>(cache.values());
    }

    /**
     * faz uma busca no cache com base na funcao passada
     */
    public <T extends SerializableEntity> T search(String key, Predicate<T> query) {
        var cache = this.<Map<Serializable, T>>get(key);
        return cache.values().stream().filter(query).findFirst().get();
    }

    /**
     * faz uma busca no cache com base na funcao passada
     */
    public <T extends SerializableEntity> List<T> searchList(String key, Predicate<T> query) {
        var cache = this.<Map<Serializable, T>>get(key);
        return cache.values().stream().filter(query).collect(Collectors.toList());
    }

    /**
     * informacoes sobre o cache
     */
    public Map<String, String> info() {
        var info = new HashMap<String, String>();
        info.put("cache size", _cache.size() + "");

        return info;
    }

}