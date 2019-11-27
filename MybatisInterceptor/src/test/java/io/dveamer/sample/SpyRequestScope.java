package io.dveamer.sample;

import io.dveamer.sample.common.scope.Attribute;
import io.dveamer.sample.common.scope.ScopeStore;

import java.util.HashMap;
import java.util.Map;

public class SpyRequestScope implements ScopeStore {

    private Map<Long, Attribute> store = new HashMap<>();


    @Override
    public Attribute getAttribute() {
        return store.getOrDefault(Thread.currentThread().getId(), new Attribute());
    }

    @Override
    public void setAttribute(Attribute attribute) {
        store.put(Thread.currentThread().getId(), attribute);
    }

    @Override
    public void removeAttribute() {
        store.remove(Thread.currentThread().getId());
    }
}
