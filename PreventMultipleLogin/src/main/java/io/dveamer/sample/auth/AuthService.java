package io.dveamer.sample.auth;

import io.dveamer.sample.common.scope.Attribute;
import io.dveamer.sample.common.scope.SessionScopeUtil;
import io.dveamer.sample.models.Authentication;
import io.dveamer.sample.models.AuthenticationRepository;
import io.dveamer.sample.models.User;
import io.dveamer.sample.models.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor=Exception.class, propagation= Propagation.REQUIRED)
public class AuthService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    UserRepository userRepository;

    @Resource(name="simpleRestTemplate")
    RestTemplate restTemplate;


    @Value("${uri.preventDuplicatedLogin}")
    private String urlPreventDuplicatedLogin;


    public Authentication login(Authentication auth) {

        logger.debug("This user is trying to log-in : {}", auth);

        User user = userRepository.findByUserName(auth.getUser().getUserName());
        logger.debug("Found user information from DB : {}", user);
        if(user==null || !user.getPswd().equals(auth.getUser().getPswd())){
            return new Authentication();
        }

        auth.setId(user.getId());
        auth.setLoggedIn(true);

        removeDuplicatedUserSession(auth);

        authenticationRepository.save(auth);

        Attribute attr = new Attribute();
        attr.setUser(user);
        SessionScopeUtil.setAttribute(attr);

        logger.debug("Success to log-in : {}", auth);

        return auth;
    }

    public Authentication currentAuthStatus() {

        Authentication auth = new Authentication();

        try{
            User user = SessionScopeUtil.getAttribute().getUser();
            if(user!=null){
                auth.setId(user.getId());
                auth.setUser(user);
                auth.setLoggedIn(true);
            }
        }catch(Exception ex){
            // do nothing.
        }

        return auth;
    }

    public Authentication logout(){
        Authentication auth = currentAuthStatus();

        if(!auth.isLoggedIn()){
            return auth;
        }

        SessionScopeUtil.setAttribute(null);

        auth.setLoggedIn(false);
        authenticationRepository.save(auth);

        logger.debug("Success to log-out : {}", auth);

        return auth;
    }


    private void removeDuplicatedUserSession(Authentication auth) {

        if(!authenticationRepository.existsById(auth.getId())){
            return;
        }

        Authentication prevAuth = authenticationRepository.getOne(auth.getId());

        if(prevAuth.getSessionId().equals(auth.getSessionId())){
            return;
        }

        logger.info("Found a duplicated session : {}", prevAuth);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", String.format("JSESSIONID=%s; Path=/;", prevAuth.getSessionId()));

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<Authentication> res = restTemplate.postForEntity(urlPreventDuplicatedLogin, entity, Authentication.class);

        if(res.getStatusCode()== HttpStatus.OK && !res.getBody().isLoggedIn()){
            logger.info("Resolved. The current status of duplicated session : {}", res.getBody());
            return;
        }

        logger.info("Failed to resolve the duplicated session problem.");
    }


}
