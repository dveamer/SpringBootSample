package io.dveamer.sample.manual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManualController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/manual")
    public void manual() {

        worst("World");
        bindOneParameter("World");

        User user = new User("dveamer@gmail.com", "Dveamer", "developer");
        bindTwoParameters(user);
        bindManyParameters(user);

        printExceptionStackTrace(user);

        User wrongUser = new User("dveamer@gmail.com", null, "developer");
        printExceptionMessage(wrongUser);
    }

    private void worst(String userName) {

        // worst
        System.out.println("Hello " + userName + ".");

        try {
            throw new Exception("Something is wrong.");
        } catch(Exception ex) {

            // worst
            ex.printStackTrace();
        }
    }

    private void bindOneParameter(String userName) {

        // poor performance, poor readability
        logger.debug("Hello " + userName + ".");

        // always good performance, poor readability
        if(logger.isDebugEnabled()) {
            logger.debug("Hello " + userName + ".");
        }

        // always good performance, good readability
        if(logger.isDebugEnabled()) {
            logger.debug("Hello {}.", userName);
        }

        // good performance, best readability - I recommend this.
        logger.debug("Hello {}.", userName);
    }

    private void bindTwoParameters(User user) {

        // poor performance, poor readability
        logger.debug("Hello " + user.getName() + "(" + user.getEmail() + ")");

        // poor performance, good readability
        logger.debug(String.format("Hello %s(%s)", user.getName(), user.getEmail()));

        // good performance, good readability - I recommend this.
        logger.debug("Hello {}({}).", user.getName(), user.getEmail());
    }

    private void bindManyParameters(User user) {

        // little poor performance, good readability
        logger.debug("User id : {}, name : {}, job : {}", user.getName(), user.getEmail(), user.getJob());

        // good performance, good readability, uncomfortable
        logger.debug("User name : {}", user.getName());
        logger.debug("User email : {}", user.getEmail());
        logger.debug("User job : {}", user.getJob());

        // good performance, good readability - I recommend this.
        logger.debug("User : {}", user);
    }


    private void printExceptionStackTrace(User user) {

        try {
            throw new Exception("Something is wrong.");
        } catch(Exception ex) {

            // good performance, poor information
            logger.error("", ex);

            // good performance, good information - I recommend this.
            logger.error("User : {}", user, ex);
        }
    }

    private void printExceptionMessage(User user) {

        try {
            if(user.getName() == null) {
                throw new IllegalArgumentException("user name is required parameter.");
            }
        } catch(IllegalArgumentException ex) {

            // good performance, poor information
            logger.warn(ex.getMessage());

            // poor performance, good information
            logger.warn("{} - User : {}", ex.getMessage(), user.toString());

            // good performance, good information - I recommend this.
            logger.warn("{} - User : {}", ex.getMessage(), user);
        }
    }


}
