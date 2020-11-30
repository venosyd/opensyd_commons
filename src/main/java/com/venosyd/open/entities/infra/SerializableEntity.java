package com.venosyd.open.entities.infra;

import java.util.Objects;

import com.venosyd.open.commons.util.JSONUtil;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public abstract class SerializableEntity {

    /** */
    private String id;

    /** */
    private String collection_key;

    /** */
    public SerializableEntity() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        var baseDTO = (SerializableEntity) o;
        var collection_check = getCollection_key().equals(baseDTO.getCollection_key());

        return Objects.equals(getId(), baseDTO.getId()) && collection_check;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return JSONUtil.toJSON(this);
    }

    /** */
    public String getId() {
        return id;
    }

    /** */
    public void setId(String id) {
        this.id = id;
    }

    /** */
    public String getCollection_key() {
        return collection_key;
    }

    /** */
    public void setCollection_key(String collection_key) {
        this.collection_key = collection_key;
    }

}
