package com.venosyd.open.commons.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Utilidades para tratar JSON
 */
public class JSONUtil {

    /**
     * objeto para json
     */
    public static String toJSON(Object objeto) {
        return createGson().toJson(objeto);
    }

    /**
     * objeto para json
     */
    public static String toJSON(Object objeto, TypeAdapterFactory adapter) {
        return registerTypeAdapter(adapter).toJson(objeto);
    }

    /**
     * json para objeto
     */
    public static <T> T fromJSON(String json) {
        return createGson().fromJson(json, new TypeToken<T>() {
        }.getType());
    }

    /**
     * json para objeto
     */
    public static <T> T fromJSON(String json, Class<T> type) {
        return createGson().fromJson(json, type);
    }

    /**
     * json para objeto
     */
    public static <T> T fromJSON(String json, Class<T> type, TypeAdapterFactory adapter) {
        return registerTypeAdapter(adapter).fromJson(json, type);
    }

    /**
     * json para lista de objetos
     */
    public static <T> List<T> fromJSONToList(String json) {
        return createGson().fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    /**
     * json para lista de objetos
     */
    public static <T> List<T> fromJSONToList(String json, TypeAdapterFactory adapter) {
        return registerTypeAdapter(adapter).fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    /**
     * json para lista de objetos
     */
    public static <T> List<T> fromJSONToList(String json, Class<T> clazz) {
        var toReturn = new ArrayList<T>();

        List<String> list = createGson().fromJson(json, new TypeToken<List<String>>() {
        }.getType());

        for (var element : list) {
            toReturn.add(fromJSON(element, clazz));
        }

        return toReturn;
    }

    /**
     * json para lista de objetos
     */
    public static <T> List<T> fromJSONToList(String json, Class<T> clazz, TypeAdapterFactory adapter) {
        var toReturn = new ArrayList<T>();

        List<String> list = registerTypeAdapter(adapter).fromJson(json, new TypeToken<List<String>>() {
        }.getType());

        for (var element : list) {
            toReturn.add(fromJSON(element, clazz));
        }

        return toReturn;
    }

    /**
     * mapa para json. nao, o metodo toJSON nao basta
     */
    public static String fromMapToJSON(Map<?, ?> map) {
        return new GsonBuilder().create().toJson(map);
    }

    /**
     * faz o inverso do de cima
     */
    public static <T, K> Map<T, K> fromJSONToMap(String json) {
        return createGson().fromJson(json, new TypeToken<Map<T, K>>() {
        }.getType());
    }

    /**
     * faz o inverso do de cima
     */
    public static <T, K> Map<T, K> fromJSONToMap(String json, TypeAdapterFactory adapter) {
        return registerTypeAdapter(adapter).fromJson(json, new TypeToken<Map<T, K>>() {
        }.getType());
    }

    /**
     * faz o inverso do de cima
     */
    @SuppressWarnings("rawtypes")
    public static <T, K> Map<T, K> fromJSONToMap(String json, Class<T> t, Class<K> k) {
        var converted = new HashMap<T, K>();

        Map map = createGson().fromJson(json, new TypeToken<Map<T, K>>() {
        }.getType());

        for (var obj : map.keySet()) {
            T key = fromJSON(toJSON(obj), t);
            K element = fromJSON(toJSON(map.get(obj)), k);
            converted.put(key, element);
        }

        return converted;
    }

    /**
     * faz o inverso do de cima
     */
    @SuppressWarnings("rawtypes")
    public static <T, K> Map<T, K> fromJSONToMap(String json, Class<T> t, Class<K> k, TypeAdapterFactory adapter) {
        var converted = new HashMap<T, K>();

        Map map = registerTypeAdapter(adapter).fromJson(json, new TypeToken<Map<T, K>>() {
        }.getType());

        for (var obj : map.keySet()) {
            T key = fromJSON(toJSON(obj), t);
            K element = fromJSON(toJSON(map.get(obj)), k);
            converted.put(key, element);
        }

        return converted;
    }

    /**
     * associa um adaptador especifico ao Gson
     */
    private static Gson registerTypeAdapter(TypeAdapterFactory adapter) {
        var b = new GsonBuilder();
        b.registerTypeAdapterFactory(adapter);
        b.registerTypeAdapter(Date.class, new DateDeserializer());
        b.registerTypeAdapter(Date.class, new DateSerializer());

        return b.create();
    }

    /**
     * gson with personalized date
     */
    private static Gson createGson() {
        var b = new GsonBuilder();
        b.registerTypeAdapter(Date.class, new DateDeserializer());
        b.registerTypeAdapter(Date.class, new DateSerializer());

        return b.create();
    }

    /**
     * desserializador de datas personalizado
     */
    private static class DateDeserializer implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2)
                throws JsonParseException {
            return json == null ? null : new Date(json.getAsLong());
        }
    }

    /**
     * desserializador de datas personalizado
     */
    private static class DateSerializer implements JsonSerializer<Date> {

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? null : new JsonPrimitive(src.getTime());
        }
    }

}
