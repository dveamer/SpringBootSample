package io.dveamer.sample.login;

import io.dveamer.sample.common.scope.SessionScopeUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    LoginService loginService;

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public User login(@RequestBody User user, HttpServletRequest request ) {
        return loginService.login(user, request.getRequestedSessionId());
    }


    @RequestMapping("/getCurrentUser")
    public User status() {
        return loginService.getCurrentUser();
    }


    @RequestMapping("/logout")
    public User logout() {
        return loginService.logout();
    }

}
