package io.dveamer.sample.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value= JsonInclude.Include.NON_ABSENT, content= JsonInclude.Include.NON_EMPTY)
public class Authentication {

    @Id
    private Long id;

    @Transient
    private User user;

    boolean isLoggedIn = false;

    @JsonIgnore
    private String sessionId;

    public Long getId() {
        return id;
    }

    public Authentication setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Authentication setUser(User user) {
        this.user = user;
        return this;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public Authentication setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
        return this;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Authentication setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authentication that = (Authentication) o;

        if (!user.equals(that.user)) return false;
        return sessionId.equals(that.sessionId);

    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + sessionId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "id=" + id +
                ", user=" + user +
                ", isLoggedIn=" + isLoggedIn +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
