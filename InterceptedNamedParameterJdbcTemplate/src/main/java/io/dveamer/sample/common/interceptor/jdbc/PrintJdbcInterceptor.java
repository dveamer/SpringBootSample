package io.dveamer.sample.common.interceptor.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PrintJdbcInterceptor implements JdbcInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String sql(String sql) {
        logger.info("SQL : {}", sql);
        return sql;
    }

    @Override
    public void param(MapSqlParameterSource paramSource) {
        Objects.requireNonNull(paramSource);

        logger.info("Parameter : {}", paramSource.getValues());
    }

    @Override
    public void param(MapSqlParameterSource[] batchArgs) {
        Objects.requireNonNull(batchArgs);

        logger.info("Count of batchUpdate parameters  : {}", batchArgs.length);
    }

    @Override
    public void output(ResultSet rs) {
        try{
            logger.info("Count of results  : {}", rs.getFetchSize());
        } catch (SQLException ex) {
            logger.error("Failed to getFetchSize", ex);
        }
    }

    @Override
    public void output(ResultSet rs, int rowNum) {
        try{
            logger.info("Count of results  : {}", rs.getFetchSize());
        } catch (SQLException ex) {
            logger.error("Failed to getFetchSize", ex);
        }
    }

    @Override
    public void output(List<Map<String, Object>> list) {
        logger.info("Count of results  : {}", list.size());
    }

    @Override
    public void output(Map<String, Object> map) {
        logger.info("result : {}", map);
    }

    @Override
    public void output(int count) {
        logger.info("changed : {}", count);
    }
}
