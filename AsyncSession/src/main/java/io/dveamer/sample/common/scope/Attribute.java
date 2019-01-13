package io.dveamer.sample.common.scope;

import io.dveamer.sample.login.User;

public class Attribute {

    public static final String KEY = "AttributeKey";

    private String loggingCode;
    private User user;

    public String getLoggingCode() {
        return loggingCode;
    }

    public Attribute setLoggingCode(String loggingCode) {
        this.loggingCode = loggingCode;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Attribute setUser(User user) {
        this.user = user;
        return this;
    }
}
