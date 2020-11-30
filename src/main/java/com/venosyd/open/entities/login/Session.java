package com.venosyd.open.entities.login;

import java.util.List;

import com.venosyd.open.entities.infra.SerializableEntity;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class Session extends SerializableEntity {

    /** */
    private String auth_user;

    /** */
    private List<String> sessions;

    /** */
    public Session() {
        setCollection_key(getClass().getSimpleName());
    }

    /** */
    public String getAuth_user() {
        return auth_user;
    }

    /** */
    public void setAuth_user(String auth_user) {
        this.auth_user = auth_user;
    }

    /** */
    public List<String> getSessions() {
        return sessions;
    }

    /** */
    public void setSessions(List<String> sessions) {
        this.sessions = sessions;
    }

}
