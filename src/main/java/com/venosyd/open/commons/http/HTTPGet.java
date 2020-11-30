package com.venosyd.open.commons.http;

import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.venosyd.open.commons.log.Debuggable;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Http Getter
 */
class HTTPGet implements Debuggable {

    // cria a chamada
    SimpleResponse get(String uri, Map<String, String> requestHeaders) {
        try {
            // cria a chamada
            var httpclient = HttpClients.createDefault();
            var httpget = new HttpGet(uri);

            // prepare headers
            for (var header : requestHeaders.keySet()) {
                httpget.setHeader(header, requestHeaders.get(header));
            }

            // send to the gods
            var response = httpclient.execute(httpget);

            // collect response
            if (response.getEntity() != null) {
                byte[] data = EntityUtils.toByteArray(response.getEntity());
                var simpleResponse = new SimpleResponse(data, response.getAllHeaders());

                // fecha conexao com os ceus
                httpclient.close();

                // process response and send back
                return simpleResponse;
            }
            //
            else {
                new SimpleResponse("".getBytes(), response.getAllHeaders());
            }

        } catch (Exception e) {
            err.exception("HTTPGET EXCEPTION", e);
        }

        return null;
    }

}
