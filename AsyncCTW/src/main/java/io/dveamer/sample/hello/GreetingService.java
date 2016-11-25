package io.dveamer.sample.hello;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GreetingService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Async("threadPoolTaskExecutor")
    public void method1() {
        logger.info("method 1-1");
        try {
            sleep(300);
        }catch(Exception ex){
            logger.error("",ex);
        }
        logger.info("method 1-2");
    }

    @Async("threadPoolTaskExecutor")
    public void method2() {
        logger.info("method 2-1");
        method3();
        logger.info("method 2-2");
    }

    @Async("threadPoolTaskExecutor")
    public void method3() {
        logger.info("method 3-1");
        try {
            sleep(100);
        }catch(Exception ex){
            logger.error("",ex);
        }
        logger.info("method 3-2");
    }

}
