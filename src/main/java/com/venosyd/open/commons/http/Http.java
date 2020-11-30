package com.venosyd.open.commons.http;

import java.util.HashMap;
import java.util.Map;

import com.venosyd.open.commons.util.JSONUtil;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         namespace que simplifica chamadas para GET e POST
 */
public abstract class Http {

    /**
     * faz uma requisicao GET e retorna um Mapa derivado de um JSON
     */
    public static SimpleResponse get(String uri) {
        return new HTTPGet().get(uri, new HashMap<String, String>());
    }

    /**
     * faz uma requisicao GET e retorna um Mapa derivado de um JSON
     */
    public static SimpleResponse get(String uri, Map<String, String> headers) {
        return new HTTPGet().get(uri, headers);
    }

    /**
     * faz uma requisicao POST e retorna um Mapa derivado de um JSON
     */
    public static SimpleResponse post(String uri, Map<String, Object> payload, Map<String, String> requestHeaders) {
        return new HTTPPost().post(uri, payload, requestHeaders);
    }

    /**
     * faz uma requisicao POST e retorna um Mapa derivado de um JSON
     */
    public static SimpleResponse post(String uri, String body, Map<String, String> requestHeaders) {
        return new HTTPPost().post(uri, body, requestHeaders);
    }

    /**
     * faz uma requisicao POST e retorna um Mapa derivado de um JSON
     */
    public static SimpleResponse post(String uri, Map<String, Object> payload) {
        return post(uri, JSONUtil.fromMapToJSON(payload));
    }

    /**
     * faz uma requisicao POST e retorna um Mapa derivado de um JSON
     */
    public static SimpleResponse post(String uri, String payload) {
        var requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/json; charset=UTF-8");
        requestHeaders.put("Accept", "*/*");
        requestHeaders.put("Accept-Encoding", "gzip,deflate,sdch");

        return new HTTPPost().post(uri, payload, requestHeaders);
    }

    /**
     * faz uma requisicao POST e retorna um Mapa derivado de um XML
     */
    public static SimpleResponse postXML(String uri, String xml, Map<String, String> requestHeaders) {
        return new HTTPPost().postXML(uri, xml, requestHeaders);
    }

    /**
     * faz uma requisicao POST e retorna um Mapa derivado de um FORM URL ENCODED
     */
    public static SimpleResponse postForm(String uri, Map<String, Object> payload, Map<String, String> requestHeaders) {
        requestHeaders.put("Content-Type", "application/x-www-form-urlencoded");
        return new HTTPPost().postForm(uri, payload, requestHeaders);
    }

    /**
     * faz uma requisicao GET e retorna um Mapa derivado de um JSON
     */
    public static SimpleResponse put(String uri) {
        return new HTTPPut().put(uri, new HashMap<String, String>());
    }

    /**
     * faz uma requisicao GET e retorna um Mapa derivado de um JSON
     */
    public static SimpleResponse put(String uri, Map<String, String> headers) {
        return new HTTPPut().put(uri, headers);
    }

    /**
     * faz uma requisicao POST e retorna um Mapa derivado de um JSON
     */
    public static SimpleResponse option(String uri, Map<String, String> requestHeaders) {
        return new HTTPOption().option(uri, requestHeaders);
    }
}
