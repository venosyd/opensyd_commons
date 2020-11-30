package com.venosyd.open.commons.services.interfaces;

import java.util.HashMap;
import java.util.Map;

import com.venosyd.open.commons.services.seeker.ServiceSeeker;
import com.venosyd.open.commons.util.RESTService;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public abstract class Address {

    /**
     * return a address by a zipCode
     */
    public static Map<String, Object> getLogradouro(String cep) {
        var payload = new HashMap<String, Object>();
        payload.put("cep", cep);

        return ServiceSeeker.builder().service("address").method("post").path("/get/new/place").body(payload).run();
    }

    /**
     * returns a list of states
     */
    public static Map<String, Object> getStates(String paisID) {
        var payload = new HashMap<String, Object>();
        payload.put("pais", paisID);

        return ServiceSeeker.builder().service("address").method("post").path("/get/states").body(payload).run();
    }

    /**
     * returns a list of cities
     */
    public static Map<String, Object> getCities(String estadoID) {
        var payload = new HashMap<String, Object>();
        payload.put("estado", estadoID);

        return ServiceSeeker.builder().service("address").method("post").path("/get/cities").body(payload).run();
    }

    /**
     * returns a lista of districts
     */
    public static Map<String, Object> getDistricts(String distritoID) {
        var payload = new HashMap<String, Object>();
        payload.put("distrito", distritoID);

        return ServiceSeeker.builder().service("address").method("post").path("/get/districts").body(payload).run();
    }

    /**
     * adds a new place. can add country, state, city, district and placetype too
     */
    public static Map<String, Object> addNewPlace(String pais, String estado, String cidade, String distrito,
            String tipologradouro, String logradouro, String cep) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("pais", pais);
        payload.put("estado", estado);
        payload.put("cidade", cidade);
        payload.put("distrito", distrito);
        payload.put("tipologradouro", tipologradouro);
        payload.put("logradouro", logradouro);
        payload.put("cep", cep);

        return ServiceSeeker.builder().service("address").method("post").path("/add/place").headers(headers)
                .body(payload).run();
    }

    /**
     * updates a place
     */
    public static Map<String, Object> updatePlace(String cep, String nome) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("cep", cep);
        payload.put("nome", nome);

        return ServiceSeeker.builder().service("address").method("post").path("/update/place").headers(headers)
                .body(payload).run();
    }

}
