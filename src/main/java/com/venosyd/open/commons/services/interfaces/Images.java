package com.venosyd.open.commons.services.interfaces;

import java.util.HashMap;
import java.util.Map;

import com.venosyd.open.commons.services.seeker.ServiceSeeker;
import com.venosyd.open.commons.util.RESTService;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public abstract class Images {

    /** */
    public static Map<String, Object> save(String collection, String item) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("collection", collection);
        payload.put("item", item);

        return ServiceSeeker.builder().service("images").method("post").path("/save").headers(headers).body(payload)
                .run();
    }

    /** */
    public static Map<String, Object> load(String collection, String item) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("collection", collection);
        payload.put("item", item);

        return ServiceSeeker.builder().service("images").method("post").path("/load").headers(headers).body(payload)
                .run();
    }
}
