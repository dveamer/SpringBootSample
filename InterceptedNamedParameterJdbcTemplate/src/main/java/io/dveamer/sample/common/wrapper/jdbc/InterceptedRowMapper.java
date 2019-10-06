package io.dveamer.sample.common.wrapper.jdbc;

import io.dveamer.sample.common.interceptor.jdbc.JdbcInterceptor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

class InterceptedRowMapper<T> implements RowMapper<T> {

    private final RowMapper<T> rowMapper;

    private final List<JdbcInterceptor> interceptors;

    public InterceptedRowMapper(RowMapper<T> rowMapper, List<JdbcInterceptor> interceptors) {
        this.rowMapper = rowMapper;
        this.interceptors = Collections.unmodifiableList(interceptors);
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        for(JdbcInterceptor listener : interceptors){
            listener.output(rs, rowNum);
        }
        return rowMapper.mapRow(rs, rowNum);
    }
}
