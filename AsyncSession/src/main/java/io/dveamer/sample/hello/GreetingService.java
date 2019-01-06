package io.dveamer.sample.hello;

import io.dveamer.sample.common.scope.Attribute;
import io.dveamer.sample.common.scope.SessionScopeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GreetingService {

    Logger logger = LoggerFactory.getLogger(getClass());

    public void test() {

        String loggingCode = UUID.randomUUID().toString().replaceAll("-", "");

        logger.info("loggingCode {} is generated", loggingCode);

        Attribute attribute = new Attribute();
        attribute.setLoggingCode(loggingCode);

        SessionScopeUtil.setAttribute(attribute);

        logger.info("loggingCode {} is stored on Session Scope", loggingCode);

        method1();

        method2();
    }

    @Async("threadPoolTaskExecutor")
    public void method1() {

        try{
            Attribute attribute = SessionScopeUtil.getAttribute();
            String loggingCode = attribute.getLoggingCode();

            logger.info("method1() > Successfully received loggingCode {}", loggingCode);
        }catch(Exception ex){
            logger.info("method1() > Failed to receive loggingCode");
        }
    }

    @Async("executorWithSession")
    public void method2() {
        try{
            Attribute attribute = SessionScopeUtil.getAttribute();
            String loggingCode = attribute.getLoggingCode();

            logger.info("method2() > Successfully received loggingCode {}", loggingCode);
        }catch(Exception ex){
            logger.info("method2() > Failed to receive loggingCode");
        }
    }

}
