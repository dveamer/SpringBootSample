package io.dveamer.sample.common.scope;

import java.util.HashMap;
import java.util.Map;

public class AsyncScopeUtil {

    private static final Map<String, Attribute> attributeMap = new HashMap<>();

    public static Attribute getAttribute() {
        return attributeMap.get(Long.toString(Thread.currentThread().getId()));
    }

    public static void setAttribute(Attribute attribute) {
        attributeMap.put(Long.toString(Thread.currentThread().getId()), attribute);
    }

    public static void removeAttribute() {
        attributeMap.remove(Long.toString(Thread.currentThread().getId()));
    }
}
