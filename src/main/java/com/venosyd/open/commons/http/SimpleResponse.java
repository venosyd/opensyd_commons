package com.venosyd.open.commons.http;

import com.venosyd.open.commons.log.Debuggable;
import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class SimpleResponse implements Debuggable {

    /** */
    private byte[] body;

    /** */
    private Map<String, Object> headers;

    /** */
    SimpleResponse(byte[] body, Header[] headers) {
        this.body = body;

        this.headers = new HashMap<>();
        for (Header header : headers) {
            this.headers.put(header.getName(), header.getValue());
        }
    }

    /** */
    public byte[] getByteArrayBody() {
        return body;
    }

    /** */
    public String getStringBody() {
        return new String(body);
    }

    /** */
    public Map<String, Object> getHeaders() {
        return headers;
    }

}
