package io.dveamer.sample.common.scope;

public class Attribute {

    public static final String KEY = "AttributeKey";
    private String loggingCode;

    public String getLoggingCode() {
        return loggingCode;
    }

    public Attribute setLoggingCode(String loggingCode) {
        this.loggingCode = loggingCode;
        return this;
    }
}
