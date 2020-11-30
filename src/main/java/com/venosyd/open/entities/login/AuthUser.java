package com.venosyd.open.entities.login;

import java.util.List;

import com.venosyd.open.entities.infra.SerializableEntity;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class AuthUser extends SerializableEntity {
    /** */
    private String email;

    /** */
    private String phone;

    /** */
    private String password;

    /** */
    private Integer authorized;

    /** */
    private Long registerdate;

    /** */
    private List<String> roles;

    /** */
    private List<String> history;

    /** */
    public AuthUser() {
        setCollection_key(getClass().getSimpleName());
    }

    /** */
    public String getEmail() {
        return email;
    }

    /** */
    public void setEmail(String email) {
        this.email = email;
    }

    /** */
    public String getPhone() {
        return phone;
    }

    /** */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** */
    public String getPassword() {
        return password;
    }

    /** */
    public void setPassword(String password) {
        this.password = password;
    }

    /** */
    public Integer getAuthorized() {
        return authorized;
    }

    /** */
    public void setAuthorized(Integer authorized) {
        this.authorized = authorized;
    }

    /** */
    public Long getRegisterdate() {
        return registerdate;
    }

    /** */
    public void setRegisterdate(Long registerdate) {
        this.registerdate = registerdate;
    }

    /** */
    public List<String> getRoles() {
        return roles;
    }

    /** */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /** */
    public List<String> getHistory() {
        return history;
    }

    /** */
    public void setHistory(List<String> history) {
        this.history = history;
    }

}