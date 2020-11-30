package com.venosyd.open.entities.support;

import com.venosyd.open.entities.infra.SerializableEntity;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class Account extends SerializableEntity {

    /**  */
    private String service;

    /**  */
    private String email;

    /**  */
    private String hash;

    /**  */
    private long registered;

    /**  */
    public Account() {
        setCollection_key(getClass().getSimpleName());
    }

    /** */
    public String getService() {
        return service;
    }

    /** */
    public void setService(String service) {
        this.service = service;
    }

    /**  */
    public String getEmail() {
        return email;
    }

    /**  */
    public void setEmail(String email) {
        this.email = email;
    }

    /**  */
    public String getHash() {
        return hash;
    }

    /**  */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**  */
    public long getRegistered() {
        return registered;
    }

    /**  */
    public void setRegistered(long registered) {
        this.registered = registered;
    }

}