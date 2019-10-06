package io.dveamer.sample.common.wrapper.jdbc;

import io.dveamer.sample.common.interceptor.jdbc.JdbcInterceptor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InterceptedResultSetExtractor<T> implements ResultSetExtractor<T> {

    private final ResultSetExtractor<T> extractor;

    private final List<JdbcInterceptor> interceptors;

    public InterceptedResultSetExtractor(ResultSetExtractor extractor, List<JdbcInterceptor> interceptors) {
        this.extractor = extractor;
        this.interceptors = interceptors;
    }

    @Override
    public T extractData(ResultSet rs) throws SQLException, DataAccessException {
        for(JdbcInterceptor listener : interceptors){
            listener.output(rs);
        }
        return extractor.extractData(rs);
    }
}
