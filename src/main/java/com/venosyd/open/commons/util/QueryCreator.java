package com.venosyd.open.commons.util;

import java.util.HashMap;
import java.util.Map;

import com.venosyd.open.commons.log.Debuggable;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class QueryCreator implements Debuggable {

    /**
     * ajuda na criacao de uma query focada em achar documentos identificando um
     * padrao em um certo campo
     */
    public static Map<String, Object> createRegexQuery(String field, String pattern) {
        var regex = new HashMap<String, Object>();
        regex.put("$regex", pattern);

        var query = new HashMap<String, Object>();
        query.put(field, regex);

        return query;
    }

    /**
     * ajuda na criacao de uma query focada em achar documentos identificando um
     * padrao em um certo campo
     */
    public static String createRegexQueryInJSON(String field, String pattern) {
        return JSONUtil.fromMapToJSON(createRegexQuery(field, pattern));
    }

    /**
     * Cria uma query simples
     */
    public static Map<String, Object> createSimpleQuery(String field, String data) {
        var query = new HashMap<String, Object>();
        query.put(field, data);

        return query;
    }

    /**
     * Cria uma query simples
     */
    public static String createSimpleQueryInJSON(String field, String data) {
        return JSONUtil.fromMapToJSON(createSimpleQuery(field, data));
    }
}
