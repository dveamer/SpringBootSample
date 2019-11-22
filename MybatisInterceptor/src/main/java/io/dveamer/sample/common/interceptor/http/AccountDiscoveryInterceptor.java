package io.dveamer.sample.common.interceptor.http;

import io.dveamer.sample.common.scope.Attribute;
import io.dveamer.sample.common.scope.RequestScopeUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccountDiscoveryInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // find account information from access token.
        String userId = "100";

        Attribute attribute = RequestScopeUtil.getAttribute();
        attribute.setUserId(userId);

        RequestScopeUtil.setAttribute(attribute);
        return true;
    }
}
