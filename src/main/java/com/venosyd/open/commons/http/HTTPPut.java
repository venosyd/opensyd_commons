package com.venosyd.open.commons.http;

import java.util.Map;

import com.venosyd.open.commons.log.Debuggable;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Http PUT
 */
class HTTPPut implements Debuggable {

    // cria a chamada
    SimpleResponse put(String uri, Map<String, String> requestHeaders) {
        try {
            // cria a chamada
            var httpclient = HttpClients.createDefault();
            var httpget = new HttpPut(uri);

            // prepare headers
            for (var header : requestHeaders.keySet()) {
                httpget.setHeader(header, requestHeaders.get(header));
            }

            // send to the gods
            var response = httpclient.execute(httpget);

            var sl = response.getStatusLine();

            // collect response
            byte[] data = (sl.getStatusCode() == 204) ? "{\"status\":\"ok\"}".getBytes()
                    : EntityUtils.toByteArray(response.getEntity());
            var simpleResponse = new SimpleResponse(data, response.getAllHeaders());

            // fecha conexao com os ceus
            httpclient.close();

            // process response and send back
            return simpleResponse;

        } catch (Exception e) {
            err.exception("HTTPPUT EXCEPTION", e);
        }

        return null;
    }

}
