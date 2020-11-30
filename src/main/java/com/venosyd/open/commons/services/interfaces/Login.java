package com.venosyd.open.commons.services.interfaces;

import java.util.HashMap;

import com.venosyd.open.commons.services.seeker.ServiceSeeker;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public abstract class Login {

    /**
     * procura saber se o token esta autorizado
     */
    public static boolean verifytoken(String token, String database) {
        var payload = new HashMap<String, Object>();
        payload.put("token", token);
        payload.put("database", database);

        var response = ServiceSeeker.builder().service("login").method("post").path("token").body(payload).run();

        return response.get("status").equals("ok");
    }

}