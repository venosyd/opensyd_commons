package com.venosyd.open.commons.services.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.venosyd.open.commons.log.Debuggable;
import com.venosyd.open.commons.services.seeker.ServiceSeeker;
import com.venosyd.open.commons.util.JSONUtil;
import com.venosyd.open.commons.util.RESTService;
import com.venosyd.open.entities.infra.SerializableEntity;

import org.bson.Document;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class Repository implements Debuggable {

    /** */
    private String database;

    /** */
    public Repository(String database) {
        this.database = database;
    }

    /**
     * conexao com o servico remoto de persistencia (base de dados) para fazer uma
     * consulta GET
     */
    public <T> T get(Class<T> clazz, String id) {
        var response = getByID(clazz.getSimpleName(), id);

        if (response.get("status").equals("ok") && !response.get("payload").equals("[]"))
            return JSONUtil.<T>fromJSON((String) response.get("payload"), clazz);
        else
            return null;
    }

    /**
     * conexao com o servico remoto de persistencia (base de dados) para fazer uma
     * consulta GET
     */
    public <T> T get(Class<T> clazz, String field, Object data) {
        return this.<T>get(clazz, new Document(field, data));
    }

    /**
     * conexao com o servico remoto de persistencia (base de dados) para fazer uma
     * consulta GET
     */
    public <T> T get(Class<T> clazz, Document query) {
        var response = getByQuery(clazz.getSimpleName(), query);

        if (response.get("status").equals("ok") && !response.get("payload").equals("[]"))
            return JSONUtil.<T>fromJSON((String) response.get("payload"), clazz);
        else
            return null;
    }

    /**
     * conexao com o servico remoto de persistencia (base de dados) para fazer uma
     * consulta LIST
     */
    public <T> List<T> list(Class<T> clazz) {
        return list(clazz, null);
    }

    /**
     * conexao com o servico remoto de persistencia (base de dados) para fazer uma
     * consulta LIST
     */
    public <T> List<T> list(Class<T> clazz, String field, Object data) {
        return this.<T>list(clazz, new Document(field, data));
    }

    /**
     * conexao com o servico remoto de persistencia (base de dados) para fazer uma
     * consulta LIST
     */
    public <T> List<T> list(Class<T> clazz, Document query) {
        var response = list(clazz.getSimpleName(), query);

        if (response.get("status").equals("ok") && !response.get("payload").equals("[]"))
            return JSONUtil.<T>fromJSONToList((String) response.get("payload"), clazz);
        else
            return null;
    }

    /**
     * conexao com o servico remoto de persistencia (base de dados) para persistir
     * um novo endereco
     */
    public void save(SerializableEntity dto) {
        if (dto.getId() == null) {
            save(dto.toString());
        } else {
            update(dto.toString());
        }
    }

    /**
     * conexao com o servico remoto de persistencia (base de dados) para persistir
     * um novo endereco
     */
    public void delete(SerializableEntity dto) {
        erase("{\"id\":\"" + dto.getId() + "\",\"collection_key\":\"" + dto.getCollection_key() + "\"}");
    }

    /**
     * gets a object by their objectid
     * 
     * { database: 'my-app' collection: 'contact' id: '7772DCA34BF3' }
     */
    public Map<String, Object> getByID(String collection, String id) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("database", database);
        payload.put("collection", collection);
        payload.put("id", id);

        return ServiceSeeker.builder().service("repository").method("post").path("/get/id").headers(headers)
                .body(payload).run();
    }

    /**
     * gets a object by a query
     * 
     * { database: 'my-app' collection: 'contact' query: { ... } }
     */
    public Map<String, Object> getByQuery(String collection, Document query) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("database", database);
        payload.put("collection", collection);
        payload.put("query", query != null ? query.toJson() : null);

        return ServiceSeeker.builder().service("repository").method("post").path("/get/query").headers(headers)
                .body(payload).run();
    }

    /**
     * gets a object by a query
     * 
     * { database: 'my-app' collection: 'contact' query: { ... } }
     */
    public Map<String, Object> getIDByQuery(String collection, Document query) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("database", database);
        payload.put("collection", collection);
        payload.put("query", query != null ? query.toJson() : null);

        return ServiceSeeker.builder().service("repository").method("post").path("/get/id/query").headers(headers)
                .body(payload).run();
    }

    /**
     * list a collection by a type
     * 
     * { database: 'my-app' collection: 'contact' query: { ... } }
     */
    public Map<String, Object> list(String collection, Document query) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("database", database);
        payload.put("collection", collection);
        payload.put("query", query != null ? query.toJson() : null);

        return ServiceSeeker.builder().service("repository").method("post").path("/list").headers(headers).body(payload)
                .run();
    }

    /**
     * list a collection by a type
     * 
     * { database: 'my-app' collection: 'contact' query: { ... } }
     */
    public Map<String, Object> listAllIDs(String collection, Document query) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("database", database);
        payload.put("collection", collection);
        payload.put("query", query != null ? query.toJson() : null);

        return ServiceSeeker.builder().service("repository").method("post").path("/list/ids").headers(headers)
                .body(payload).run();
    }

    /**
     * lists distinct objects by a field of a type
     * 
     * { database: 'my-app' collection: 'contact' field: 'phone' query: { ... } }
     */
    public Map<String, Object> listDistinct(String collection, String field, Document query) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("database", database);
        payload.put("collection", collection);
        payload.put("field", field);
        payload.put("query", query != null ? query.toJson() : null);

        return ServiceSeeker.builder().service("repository").method("post").path("/list/distinct").headers(headers)
                .body(payload).run();
    }

    /**
     * lista elementos de uma lista, a partir de um indice
     * 
     * { database: 'my-app' collection: 'contact' skip: 217381 query: { ... } }
     */
    public Map<String, Object> skipList(String collecction, int skip, Document query) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("database", database);
        payload.put("skip", skip + "");
        payload.put("query", query != null ? query.toJson() : null);

        return ServiceSeeker.builder().service("repository").method("post").path("/list/skip").headers(headers)
                .body(payload).run();
    }

    /**
     * saves new instances
     * 
     * { database: 'my-app' payload: { ... } }
     */
    public Map<String, Object> save(String payload) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var body = new HashMap<String, Object>();
        body.put("database", database);
        body.put("payload", payload);

        return ServiceSeeker.builder().service("repository").method("post").path("/save").headers(headers).body(body)
                .run();
    }

    /**
     * updates new instances
     * 
     * { database: 'my-app' payload: { ... } }
     */
    public Map<String, Object> update(String payload) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var body = new HashMap<String, Object>();
        body.put("database", database);
        body.put("payload", payload);

        return ServiceSeeker.builder().service("repository").method("post").path("/update").headers(headers).body(body)
                .run();
    }

    /**
     * erase instance
     * 
     * { database: 'my-app' payload: { ... } }
     */
    public Map<String, Object> erase(String payload) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var body = new HashMap<String, Object>();
        body.put("database", database);
        body.put("payload", payload);

        return ServiceSeeker.builder().service("repository").method("post").path("/erase").headers(headers).body(body)
                .run();
    }

    /**
     * conta quantas instancias tem na colecao
     * 
     * { database: 'my-app' collection: Collection }
     */
    public Map<String, Object> count(String collection) {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + RESTService.DEFAULT_TOKEN);

        var payload = new HashMap<String, Object>();
        payload.put("database", database);
        payload.put("collection", collection);

        return ServiceSeeker.builder().service("repository").method("post").path("/count").headers(headers)
                .body(payload).run();
    }

}