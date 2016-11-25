package io.dveamer.sample.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

@RestController
public class GreetingController {

    @Resource
    GreetingService greetingService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {

        logger.info("Hello {}", name);
        greetingService.method1();
        greetingService.method2();

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
}
