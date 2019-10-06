package io.dveamer.sample.common.scope;

public class Attribute {

    public static final String KEY = "AttributeKey";

    private String loggingCode;
    private String userId;

    public String getLoggingCode() {
        return loggingCode;
    }

    public Attribute setLoggingCode(String loggingCode) {
        this.loggingCode = loggingCode;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Attribute setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}