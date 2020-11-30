package com.venosyd.open.commons.http;

import java.util.Map;
import java.util.stream.Collectors;

import com.venosyd.open.commons.log.Debuggable;
import com.venosyd.open.commons.util.JSONUtil;

import org.apache.http.Consts;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Http Poster
 */
class HTTPPost implements Debuggable {
    /** */
    SimpleResponse post(String uri, Map<String, Object> payload, Map<String, String> requestHeaders) {
        return post(uri, JSONUtil.fromMapToJSON(payload), requestHeaders);
    }

    /** */
    SimpleResponse post(String uri, String body, Map<String, String> requestHeaders) {
        try {
            // cria a chamada
            var httpclient = HttpClients.createDefault();
            var httppost = new HttpPost(uri);

            // converte o payload de mapa para json
            var jsonparam = new StringEntity(body, "UTF-8");
            jsonparam.setContentType("application/json");
            jsonparam.setChunked(true);

            // prepare headers
            requestHeaders.remove("Content-Length");
            requestHeaders.remove("content-length");

            for (var header : requestHeaders.keySet()) {
                httppost.setHeader(header, requestHeaders.get(header));
            }

            httppost.setEntity(jsonparam);

            // send to the gods
            var response = httpclient.execute(httppost);

            // collect response
            byte[] data = EntityUtils.toByteArray(response.getEntity());
            var simpleResponse = new SimpleResponse(data, response.getAllHeaders());

            // close the connection to the heavens
            httpclient.close();

            // process response and send back
            return simpleResponse;

        } catch (Exception e) {
            err.exception("HTTPPOST EXCEPTION", e);
            err.ln_(e);

            e.printStackTrace();
        }

        return null;
    }

    /**
     * Post especifico para url-encoded-forms
     */
    SimpleResponse postXML(String uri, String xml, Map<String, String> requestHeaders) {
        try {
            // cria a chamada
            var httpclient = HttpClients.createDefault();
            var httppost = new HttpPost(uri);

            // converte o payload de mapa para json
            var xmlparam = new StringEntity(xml, ContentType.create("text/xml", Consts.ISO_8859_1));
            xmlparam.setChunked(true);

            // prepare headers
            requestHeaders.remove("Content-Length");
            requestHeaders.remove("content-length");

            for (var header : requestHeaders.keySet()) {
                httppost.setHeader(header, requestHeaders.get(header));
            }

            httppost.setEntity(xmlparam);

            // send to the gods
            var response = httpclient.execute(httppost);

            // collect response
            byte[] data = EntityUtils.toByteArray(response.getEntity());
            var simpleResponse = new SimpleResponse(data, response.getAllHeaders());

            // close the connection to the heavens
            httpclient.close();

            // process response and send back
            return simpleResponse;

        } catch (Exception e) {
            err.exception("HTTPPOST XML EXCEPTION", e);
            err.ln_(e);

            e.printStackTrace();
        }

        return null;
    }

    /**
     * Post especifico para url-encoded-forms
     */
    SimpleResponse postForm(String uri, Map<String, Object> payload, Map<String, String> requestHeaders) {
        try {
            // cria a chamada
            var httpclient = HttpClients.createDefault();
            var httppost = new HttpPost(uri);

            // converte o payload de mapa para json
            var nvps = payload.entrySet().stream()
                    .map(entry -> new BasicNameValuePair(entry.getKey(), (String) entry.getValue()))
                    .collect(Collectors.toList());

            // prepare headers
            for (var header : requestHeaders.keySet()) {
                requestHeaders.remove("Content-Length");
                requestHeaders.remove("content-length");

                httppost.setHeader(header, requestHeaders.get(header));
            }

            httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

            // send to the gods
            var response = httpclient.execute(httppost);

            // collect response
            byte[] data = EntityUtils.toByteArray(response.getEntity());
            var simpleResponse = new SimpleResponse(data, response.getAllHeaders());

            // close the connection to the heavens
            httpclient.close();

            // process response and send back
            return simpleResponse;

        } catch (Exception e) {
            err.exception("HTTPPOST EXCEPTION", e);
        }

        return null;
    }

}
