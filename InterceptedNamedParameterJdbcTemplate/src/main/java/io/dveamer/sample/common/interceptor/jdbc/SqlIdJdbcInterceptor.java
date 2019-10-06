package io.dveamer.sample.common.interceptor.jdbc;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class SqlIdJdbcInterceptor implements JdbcInterceptor {

    private final String SqlId_COMMENT = " /* SQL_ID : %s */ ";

    private final String POSTFIX_REPOSITORY = "Repo";

    @Override
    public String sql(String sql) {
        return String.format(SqlId_COMMENT, extractSqlId()) + sql;
    }

    @Override
    public void param(MapSqlParameterSource paramSource) {
        // do nothing.
    }

    @Override
    public void param(MapSqlParameterSource[] batchArgs) {
        // do nothing.
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

    private String extractSqlId(){
        String sqlId = "unknown.sql_id";
        try{
            StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
            for(int i=5; i<10; i++){
                if(steArray[i].getClassName().endsWith(POSTFIX_REPOSITORY)){
                    return steArray[i].getClassName().substring(steArray[i].getClassName().lastIndexOf(".")+1) + "." + steArray[i].getMethodName();
                }
            }
            return sqlId;
        }catch(Exception ex){
            return sqlId;
        }
    }
}
