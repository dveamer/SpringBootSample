package io.dveamer.sample.login;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value= JsonInclude.Include.NON_ABSENT, content= JsonInclude.Include.NON_EMPTY)
public class User {

    private String id;

    private String pswd;

    private boolean isLoginedIn = false;

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    @JsonIgnore
    public String getPswd() {
        return pswd;
    }

    public User setPswd(String pswd) {
        this.pswd = pswd;
        return this;
    }

    public boolean isLoginedIn() {
        return isLoginedIn;
    }

    public User setLoginedIn(boolean loginedIn) {
        isLoginedIn = loginedIn;
        return this;
    }
}
