package io.dveamer.sample;

import io.dveamer.sample.common.scope.Attribute;
import io.dveamer.sample.common.scope.AsyncScopeUtil;
import io.dveamer.sample.common.scope.SessionScopeUtil;
import org.springframework.context.annotation.*;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableAsync(mode=AdviceMode.ASPECTJ)
public class SpringAsyncConfig {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected Logger errorLogger = LoggerFactory.getLogger("error");

    @Bean(name = "threadPoolTaskExecutor", destroyMethod = "shutdown")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(30);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("Executor-");
        taskExecutor.initialize();

        return taskExecutor;
    }

    @Bean(name = "executorWithSession", destroyMethod = "destroy")
    public Executor executorWithSession() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(30);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("ExecutorWithSession-");
        taskExecutor.initialize();

        return new HandlingExecutor(taskExecutor); // HandlingExecutor로 wrapping 합니다.
    }

    public class HandlingExecutor implements AsyncTaskExecutor {
        private AsyncTaskExecutor executor;

        public HandlingExecutor(AsyncTaskExecutor executor) {
            this.executor = executor;
        }

        @Override
        public void execute(Runnable task) {
            executor.execute(createWrappedRunnable(task));
        }

        @Override
        public void execute(Runnable task, long startTimeout) {
            executor.execute(createWrappedRunnable(task), startTimeout);
        }

        @Override
        public Future<?> submit(Runnable task) {
            return executor.submit(createWrappedRunnable(task));
        }

        @Override
        public <T> Future<T> submit(final Callable<T> task) {
            return executor.submit(createCallable(task));
        }

        private <T> Callable<T> createCallable(final Callable<T> task) {
            Attribute attr = SessionScopeUtil.getAttribute();

            return new AttributeAwareTask(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    try {
                        return task.call();
                    } catch (Exception ex) {
                        handle(ex);
                        throw ex;
                    }
                }
            }, attr);
        }

        private Runnable createWrappedRunnable(final Runnable task) {
            Attribute attr = SessionScopeUtil.getAttribute();

            return new AttributeAwareTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        task.run();
                    } catch (Exception ex) {
                        handle(ex);
                    }
                }
            }, attr);
        }

        private void handle(Exception ex) {
            errorLogger.info("Failed to execute callable. : {}", ex.getMessage());
            errorLogger.error("Failed to execute callable. ",ex);
        }

        public void destroy() {
            if(executor instanceof ThreadPoolTaskExecutor){
                ((ThreadPoolTaskExecutor) executor).shutdown();
            }
        }
    }

    public class AttributeAwareTask<T> implements Callable<T>, Runnable {
        private Callable<T> callable;
        private Runnable runnable;
        private Attribute attribute;

        public AttributeAwareTask(Callable<T> task, Attribute attribute) {
            this.callable = task;
            this.attribute = attribute;
        }

        public AttributeAwareTask(Runnable task, Attribute attribute) {
            this.runnable = task;
            this.attribute = attribute;
        }

        @Override
        public T call() throws Exception {
            AsyncScopeUtil.setAttribute(attribute);
            try {
                return callable.call();
            } finally {
                AsyncScopeUtil.removeAttribute();
            }
        }

        @Override
        public void run() {
            AsyncScopeUtil.setAttribute(attribute);
            try {
                runnable.run();
            } finally {
                AsyncScopeUtil.removeAttribute();
            }
        }
    }


}
