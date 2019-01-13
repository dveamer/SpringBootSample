package io.dveamer.sample.auth;

import io.dveamer.sample.models.Authentication;
import io.dveamer.sample.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    AuthService authService;

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public Authentication login(@RequestBody User user, HttpServletRequest request) {
        Authentication auth = new Authentication()
                .setUser(user)
                .setSessionId(request.getSession().getId());

        return authService.login(auth);
    }

    @RequestMapping(value="/status", method=RequestMethod.GET)
    public Authentication status() {
        return authService.currentAuthStatus();
    }

    @RequestMapping(value="/logout", method=RequestMethod.POST)
    public Authentication logout() {
        return authService.logout();
    }

}
