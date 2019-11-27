package io.dveamer.sample.common.scope;

public class RequestScopeUtil {

    private static ScopeStore scopeStore;

    public static synchronized void defineScope(ScopeStore inputScopeStore){
        if(scopeStore!=null){
            return;
        }
        scopeStore = inputScopeStore;
    }


    public static Attribute getAttribute() {

        if(scopeStore ==null){
            defineScope(new RequestScope());
        }

        return scopeStore.getAttribute();
    }

    public static void setAttribute(Attribute attribute) {

        if(scopeStore ==null){
            defineScope(new RequestScope());
        }

        scopeStore.setAttribute(attribute);
    }

    public static void removeAttribute() {

        if(scopeStore ==null){
            defineScope(new RequestScope());
        }
        scopeStore.removeAttribute();
    }
}