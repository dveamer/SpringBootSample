package io.dveamer.sample.common.scope;

public interface ScopeStore {

    Attribute getAttribute();

    void setAttribute(Attribute attribute);

    void removeAttribute();
}
