package com.venosyd.open.commons.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.venosyd.open.commons.log.Debuggable;
import com.venosyd.open.commons.services.interfaces.Login;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         venosyd © 2016-2020
 */
public interface RESTService extends Debuggable {

    // Base64
    String DEFAULT_TOKEN = "YzM5NDYzNzEwOGFlM2ZiMzhlNWMyYWNjOGRkNjczNTIxOTA2Zjc5NDI1MThhMjE2NThlM2EwMjQzZDk0NDc1ZA==";

    /**
     * PUBLIC
     */
    default Response process(String body, Function<Map<String, String>, Response> operation, String module) {
        if (body == null)
            body = "{}";

        try {
            return operation.apply(JSONUtil.fromJSONToMap(body));
        } catch (Exception e) {
            return makeErrorResponse("Erro no modulo " + module);
        }
    }

    /**
     * PUBLIC, HEADER AUTH AND AUTH TOKEN
     */
    default Response process(Map<String, String> request, Function<Map<String, String>, Response> operation,
            String module) {
        try {
            return operation.apply(request);
        } catch (Exception e) {
            e.printStackTrace();
            return makeErrorResponse("Erro no modulo " + module + ": " + e);
        }
    }

    /**
     * PRIVATE, NO HEADER AUTH
     */
    default Response process(Map<String, String> request, Function<Map<String, String>, Response> operation) {
        if (request.containsKey("token")) {
            if (request.get("token").equals(DEFAULT_TOKEN)) {
                return operation.apply(request);
            }
            //
            else {
                var authorizedtoken = Login.verifytoken(request.get("token"),
                        request.getOrDefault("logindb", request.get("database")));

                if (authorizedtoken)
                    return operation.apply(request);
            }

        }

        return makeErrorResponse("Token inválido");
    }

    /**
     * PRIVATE, HEADER AUTH
     */
    default Response process(Map<String, String> request, String authorization, List<String> arguments,
            Function<Map<String, String>, Response> operation) {
        try {
            var missing = checkarguments(arguments, request);

            if (missing != null)
                return makeErrorResponse("Missing '" + missing + "' field");

            if (authorization != null && authorization.startsWith("Basic")) {
                if (authorization.contains(DEFAULT_TOKEN)) {
                    return operation.apply(request);
                }
                //
                else {
                    var token = new String(Base64.getDecoder().decode(authorization.replace("Basic ", "")));
                    var authorizedtoken = Login.verifytoken(token,
                            request.getOrDefault("logindb", request.get("database")));

                    if (authorizedtoken)
                        return operation.apply(request);
                }

            }
        } catch (Exception e) {
            err.exception("", e);
        }

        return makeErrorResponse("Não autorizado");
    }

    /**
     * verifica se o json passado contem todos os argumentos necessarios e retorna
     * nulo se tiver ok, se voltar algo eh o que esta faltando
     */
    default String checkarguments(List<String> arguments, Map<String, String> request) {
        if (arguments == null)
            arguments = new ArrayList<>();

        for (var argument : arguments)
            if (!request.containsKey(argument))
                return argument;

        return null;
    }

    /**
     * retorna o conteudo do header Authorization
     */
    default String getauthcode(HttpHeaders headers) {
        var header = headers.getRequestHeaders().get("Authorization");
        return header != null ? header.get(0) : null;
    }

    /**
     * constroi a resposta
     */
    default Response makeResponse(Map<String, String> result) {
        return makeResponse(result, true);
    }

    /**
     * constroi a resposta
     */
    default Response makeResponse(Map<String, String> result, boolean zip) {
        var payload = JSONUtil.fromMapToJSON(result);
        return Response.ok(zip ? zip(payload) : payload, MediaType.APPLICATION_JSON).build();
    }

    /**
     * constroi a resposta de erro
     */
    default Response makeErrorResponse(String message) {
        var result = new HashMap<String, String>();
        result.put("status", "error");
        result.put("message", message);

        return Response.status(400, zip(JSONUtil.fromMapToJSON(result))).build();
    }

    /**
     * 
     */
    default String zip(String body) {
        // var decoded = Base64.getEncoder().encode(GZipUtil.zip(body));
        // return new String(decoded);
        return body;
    }

    /**
     * 
     */
    default String unzip(String body) {
        // var decoded = Base64.getDecoder().decode(body.getBytes());
        // return GZipUtil.unzip(decoded);
        return body;
    }

}