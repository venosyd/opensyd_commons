package com.venosyd.open.commons.services.seeker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.venosyd.open.commons.http.Http;
import com.venosyd.open.commons.log.Debuggable;
import com.venosyd.open.commons.util.Config;
import com.venosyd.open.commons.util.JSONUtil;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Service Register in SSK implementation
 */
class ServiceRegistryImpl implements ServiceRegistry, Debuggable {

    /** URL do ServiceSeeker */
    private String _ssiUrl;

    /** cache de servicos ja solicitados */
    private Map<String, String> _services = new HashMap<>();

    /** cache de hosts ja solicitados */
    private Map<String, String> _hosts = new HashMap<>();

    ServiceRegistryImpl() {
        _ssiUrl = Config.INSTANCE.<Map<String, String>>get("locations").get("ssi");
    }

    @Override
    public String registerInSS(String service, String privateURL, List<String> publicURLs) {
        try {
            // payload map
            var payload = new HashMap<String, Object>();
            payload.put("service", service);
            payload.put("privateURL", privateURL);
            payload.put("publicURLs", JSONUtil.toJSON(publicURLs));

            // request to server
            var httpresponse = Http.post(_ssiUrl + "/register", payload);
            var response = JSONUtil.<String, String>fromJSONToMap(httpresponse.getStringBody());

            // dependendo da resposta, retorna o resultado
            if (response.get("status").equals("ok")) {
                return "Service registered in SSK successfully";
            } else {
                return "Unreacheable SSK. Can't register service: " + response.get("message");
            }
        } catch (Exception e) {
            err.exception("SSK REGISTER ERROR", e);

            return "ServiceSeeker unaccessible";
        }
    }

    @Override
    public String getService(String service) {
        // servico nao esta listado?
        if (!_services.containsKey(service)) {
            // pede ao ServiceSeeker a url
            var result = _askFor(service);

            // se tiver OK, retorna a url do servico
            if (result.equals("ServiceSeeker found with success")) {
                return _services.get(service);
            }

            // senao, retorna a mensagem de erro
            else {
                return result;
            }
        } else {
            // se ja tiver listado, retorna a url
            return _services.get(service);
        }
    }

    @Override
    public String getHost(String host) {
        // servico nao esta listado?
        if (!_hosts.containsKey(host)) {
            // pede ao ServiceSeeker a url
            var result = _askHost(host);

            // se tiver OK, retorna a url do servico
            if (result.equals("ServiceSeeker found with success")) {
                return _hosts.get(host);
            }

            // senao, retorna a mensagem de erro
            else {
                return result;
            }
        } else {
            // se ja tiver listado, retorna a url
            return _hosts.get(host);
        }
    }

    /**
     * Os SSI padrao da whatever usam como resposta o padrao
     * 
     * { status: open / closed / failure / unreached url:
     * http://<ip>:<port>/<service> }
     */
    private String _askHost(String host) {
        try {
            var payload = new HashMap<String, Object>();
            payload.put("host", host);

            var httpresponse = Http.post(_ssiUrl + "/host", payload);
            var response = JSONUtil.<String, String>fromJSONToMap(httpresponse.getStringBody());

            if (response.get("status").equals("open")) {
                _hosts.put(host, response.get("url"));
                return "ServiceSeeker found with success";

            } else if (response.get("status").equals("closed")) {
                return "Requested hosts is closed";

            } else if (response.get("status").equals("failure")) {
                return "Failure in requested hosts search";

            } else if (response.get("status").equals("unreached")) {
                return "Unreached hosts";
            }
        } catch (Exception e) {
            err.tag("SSK ASK HOST EXCEPTION").ln(e);
        }

        return "ServiceSeeker unaccessible";
    }

    /**
     * Os SSI padrao da whatever usam como resposta o padrao
     * 
     * { status: open / closed / failure / unreached url:
     * http://<ip>:<port>/<service> }
     */
    private String _askFor(String name) {
        try {
            var payload = new HashMap<String, Object>();
            payload.put("askFor", name);

            var httpresponse = Http.post(_ssiUrl + "/ask", payload);
            var response = JSONUtil.<String, String>fromJSONToMap(httpresponse.getStringBody());

            if (response.get("status").equals("open")) {
                _services.put(name, response.get("url"));
                return "ServiceSeeker found with success";

            } else if (response.get("status").equals("closed")) {
                return "Requested Service is closed";

            } else if (response.get("status").equals("failure")) {
                return "Failure in requested service search";

            } else if (response.get("status").equals("unreached")) {
                return "Unreached service";
            }
        } catch (Exception e) {
            err.tag("SSK ASK FOR EXCEPTION").ln(e);
        }

        return "ServiceSeeker unaccessible";
    }

}
