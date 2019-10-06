package io.dveamer.sample.common.interceptor.jdbc;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface JdbcInterceptor {

    String sql(String sql);

    void param(MapSqlParameterSource paramSource);

    void param(MapSqlParameterSource[] batchArgs);

    void output(ResultSet rs);

    void output(ResultSet rs, int rowNum);

    void output(List<Map<String, Object>> list);

    void output(Map<String, Object> map);

    void output(int count);
}
