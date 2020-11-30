package com.venosyd.open.commons.services.interfaces;

import java.util.HashMap;
import java.util.Map;

import com.venosyd.open.commons.services.seeker.ServiceSeeker;
import com.venosyd.open.commons.util.RESTService;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public abstract class Mail {

    /**
     * envia um email mandando a mensagem para o servico especifico
     */
    public static Map<String, Object> send(Map<String, Object> params) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);
        params.put("database", "e5d67ad6f5af2e4bf29c5de07a24c61a");

        return ServiceSeeker.builder().service("mail").method("post").path("/send").headers(headers).body(params).run();
    }

}