package com.venosyd.open.commons.http;

import java.util.Map;

import org.apache.http.client.methods.HttpOptions;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.venosyd.open.commons.log.Debuggable;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Http Optioner
 */
class HTTPOption implements Debuggable {

    SimpleResponse option(String uri, Map<String, String> requestHeaders) {
        try {
            // cria a chamada
            var httpclient = HttpClients.createDefault();
            var httpoption = new HttpOptions(uri);

            // prepare headers
            requestHeaders.forEach((k, v) -> httpoption.setHeader(k, v));

            // send to the gods
            var response = httpclient.execute(httpoption);

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
            err.exception("HTTPOPTION EXCEPTION", e);
        }

        return null;
    }

}
