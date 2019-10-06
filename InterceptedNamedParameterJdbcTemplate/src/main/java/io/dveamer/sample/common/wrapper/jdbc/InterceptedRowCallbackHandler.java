package io.dveamer.sample.common.wrapper.jdbc;

import io.dveamer.sample.common.interceptor.jdbc.JdbcInterceptor;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InterceptedRowCallbackHandler implements RowCallbackHandler {

    private final RowCallbackHandler handler;

    private final List<JdbcInterceptor> interceptors;

    public InterceptedRowCallbackHandler(RowCallbackHandler handler, List<JdbcInterceptor> interceptors) {
        this.handler = handler;
        this.interceptors = interceptors;
    }

    @Override
    public void processRow(ResultSet rs) throws SQLException {
        for(JdbcInterceptor listener : interceptors){
            listener.output(rs);
        }
        handler.processRow(rs);
    }
}
