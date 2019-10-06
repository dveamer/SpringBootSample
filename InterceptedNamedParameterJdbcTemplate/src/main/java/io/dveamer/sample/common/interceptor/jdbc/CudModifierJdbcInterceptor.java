package io.dveamer.sample.common.interceptor.jdbc;

import io.dveamer.sample.common.scope.Attribute;
import io.dveamer.sample.common.scope.RequestScopeUtil;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class CudModifierJdbcInterceptor implements JdbcInterceptor {

    private final String INSERT_COLS_MARK = "/*INSERT_COLUMNS*/";
    private final String INSERT_VALS_MARK = "/*INSERT_VALUES*/";
    private final String UPDATE_VALS_MARK = "/*UPDATE_VALUES*/";

    private final String INSERT_COLS = ", created_dt, created_by, updated_dt, updated_by ";
    private final String INSERT_VALS = ", sysdate, :updatedBy, sysdate, :updatedBy ";
    private final String UPDATE_VALS = ", updated_dt = sysdate, updated_by = :updateBy ";

    @Override
    public String sql(String sql) {
        String replacedSql = sql.replace(UPDATE_VALS_MARK, UPDATE_VALS);

        if(sql.length()!=replacedSql.length()){
            return replacedSql;
        }

        replacedSql = sql.replace(INSERT_COLS_MARK, INSERT_COLS);
        if(sql.length()!=replacedSql.length()){
            return replacedSql.replace(INSERT_VALS_MARK, INSERT_VALS);
        }

        return sql;
    }

    @Override
    public void param(MapSqlParameterSource paramSource) {
        Attribute attribute = RequestScopeUtil.getAttribute();
        paramSource.addValue("updatedBy", attribute.getUserId());
    }

    @Override
    public void param(MapSqlParameterSource[] batchArgs) {
        Attribute attribute = RequestScopeUtil.getAttribute();
        for(MapSqlParameterSource batchArg : batchArgs){
            batchArg.addValue("updatedBy", attribute.getUserId());
        }
    }

    @Override
    public void output(ResultSet rs) {
        // do nothing.
    }

    @Override
    public void output(ResultSet rs, int rowNum) {
        // do nothing.
    }

    @Override
    public void output(List<Map<String, Object>> list) {
        // do nothing.
    }

    @Override
    public void output(Map<String, Object> map) {
        // do nothing.
    }

    @Override
    public void output(int count) {
        // do nothing.
    }
}
