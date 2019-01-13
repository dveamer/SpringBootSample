package io.dveamer.sample.login;

import io.dveamer.sample.common.scope.Attribute;
import io.dveamer.sample.common.scope.SessionScopeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    Logger logger = LoggerFactory.getLogger(getClass());

    public User login(User user, String sessionId) {

        removeDuplicatedUserSession(user,sessionId);

        return user;
    }

    public User getCurrentUser() {
        try{
            User user = SessionScopeUtil.getAttribute().getUser();
            if(user!=null){
                return user;
            }
        }catch(Exception ex){
            // do nothing.
        }
        return new User();
    }

    public User logout() {
        User user = getCurrentUser();
        if(user.isLoginedIn()){
            SessionScopeUtil.getAttribute().setUser(null);
            user.setLoginedIn(false);
        }

        return user;
    }

    private void removeDuplicatedUserSession(User user, String sessionId) {

    }

}
