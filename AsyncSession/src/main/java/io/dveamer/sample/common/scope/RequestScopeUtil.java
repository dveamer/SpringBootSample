package io.dveamer.sample.common.scope;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RequestScopeUtil {

    public static Attribute getAttribute() {
        return (Attribute) RequestContextHolder.getRequestAttributes().getAttribute(Attribute.KEY, RequestAttributes.SCOPE_REQUEST);
    }

    public static void setAttribute(Attribute attribute) {
        RequestContextHolder.getRequestAttributes().setAttribute(Attribute.KEY, attribute, RequestAttributes.SCOPE_REQUEST);
    }

    public static void removeAttribute() {
        RequestContextHolder.getRequestAttributes().removeAttribute(Attribute.KEY, RequestAttributes.SCOPE_REQUEST);
    }
}
