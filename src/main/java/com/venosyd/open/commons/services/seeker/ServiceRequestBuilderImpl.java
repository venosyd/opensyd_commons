package com.venosyd.open.commons.services.seeker;

import java.util.HashMap;
import java.util.Map;

import com.venosyd.open.commons.http.Http;
import com.venosyd.open.commons.http.SimpleResponse;
import com.venosyd.open.commons.util.JSONUtil;
import com.venosyd.open.commons.util.RESTService;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Service Request Builder Implementation
 */
class ServiceRequestBuilderImpl implements ServiceRequestBuilder, RESTService {

    /**
     * servico requisitado
     */
    private String _service;

    /**
     * metodo POST ou GET
     */
    private String _method;

    /**
     * path que corresponde ao que o cliente quer do servico
     */
    private String _path;

    /**
     * em caso de POST, o json com o payload
     */
    private Map<String, String> _headers;

    /**
     * em caso de POST, o json com o payload
     */
    private Map<String, Object> _body;

    @Override
    public ServiceRequestBuilder service(String service) {
        _service = service;
        return this;
    }

    @Override
    public ServiceRequestBuilder method(String method) {
        _method = method.toUpperCase();
        return this;
    }

    @Override
    public ServiceRequestBuilder path(String path) {
        _path = path.startsWith("/") ? path : "/" + path;
        return this;
    }

    @Override
    public ServiceRequestBuilder headers(Map<String, String> headers) {
        _headers = headers;
        return this;
    }

    @Override
    public ServiceRequestBuilder body(Map<String, Object> body) {
        _body = body;
        return this;
    }

    @Override
    @SuppressWarnings("serial")
    public Map<String, Object> run() {
        // checkagem se usuario definiu servico
        if (_service == null || _service.isEmpty()) {
            return new HashMap<String, Object>() {
                {
                    put("status", "error");
                    put("message", "[SVSK-6105c2da]The requested service name is missing");
                }
            };
        }

        // checkagem se usuario definiu metodo
        if (_method == null || _method.isEmpty()) {
            return new HashMap<String, Object>() {
                {
                    put("status", "error");
                    put("message", "[SVSK-0899a2cd]The method to call requested service is missing");
                }
            };
        }

        // checkagem se usuario definiu o path
        if (_path == null || _path.isEmpty()) {
            return new HashMap<String, Object>() {
                {
                    put("status", "error");
                    put("message", "[SVSK-635d6983]There's no path to call requested service");
                }
            };
        }

        // checkagem se usuario definiu metodo POST sem body
        if (_method.equals("POST") && (_body == null || _body.isEmpty())) {
            return new HashMap<String, Object>() {
                {
                    put("status", "error");
                    put("message", "[SVSK-03b5e7f9]POST request without a json body");
                }
            };
        }

        try {
            var serviceURL = ServiceRegistry.INSTANCE.getService(_service);

            if (serviceURL.startsWith("http")) {
                SimpleResponse response;

                if (_method.equals("GET")) {
                    if (_headers == null || _headers.isEmpty())
                        response = Http.get(serviceURL + _path);
                    else
                        response = Http.get(serviceURL + _path, _headers);
                } else {
                    if (_headers == null || _headers.isEmpty())
                        response = Http.post(serviceURL + _path, zip(JSONUtil.fromMapToJSON(_body)));
                    else
                        response = Http.post(serviceURL + _path, zip(JSONUtil.fromMapToJSON(_body)), _headers);
                }

                var responsebody = unzip(response.getStringBody());

                // if nothing was found
                if (responsebody.equals("Not Found")) {
                    return new HashMap<String, Object>() {
                        {
                            put("status", "error");
                            put("message", "Error 404");
                        }
                    };
                }

                // if shit happen
                if (responsebody.equals("Internal Server Error")) {
                    return new HashMap<String, Object>() {
                        {
                            put("status", "error");
                            put("message", "Error 500");
                        }
                    };
                }

                return JSONUtil.<String, Object>fromJSONToMap(responsebody);

            } else {
                return new HashMap<String, Object>() {
                    {
                        put("status", "error");
                        put("message", "[SVSK-7ed19bf0]Unreached service");
                    }
                };
            }

        } catch (Exception e) {
            err.exception("SERVICE REQUEST BUILDER", e);

            return new HashMap<String, Object>() {
                {
                    put("status", "error");
                    put("message", "[SVSK-fb1833a7]ServiceSeeker unaccessible");
                }
            };
        }
    }

}
