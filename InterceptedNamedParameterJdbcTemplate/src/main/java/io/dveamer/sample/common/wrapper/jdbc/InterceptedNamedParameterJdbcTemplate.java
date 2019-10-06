package io.dveamer.sample.common.wrapper.jdbc;

import io.dveamer.sample.common.interceptor.jdbc.JdbcInterceptor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InterceptedNamedParameterJdbcTemplate implements NamedParameterJdbcOperations{

    private final String PROJECT_NAME = "sample";
    
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final List<JdbcInterceptor> queryInterceptors;
    private final List<JdbcInterceptor> reverseQueryInterceptors;

    private final List<JdbcInterceptor> commandInterceptors;
    private final List<JdbcInterceptor> reverseCommandInterceptors;

    public InterceptedNamedParameterJdbcTemplate(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this(namedParameterJdbcOperations, null, null);
    }

    public InterceptedNamedParameterJdbcTemplate(NamedParameterJdbcOperations namedParameterJdbcOperations
            , List<JdbcInterceptor> queryInterceptors
            , List<JdbcInterceptor> commandInterceptors) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;

        if(queryInterceptors ==null){
            this.queryInterceptors = Collections.EMPTY_LIST;
            this.reverseQueryInterceptors = Collections.EMPTY_LIST;
        }else{
            this.queryInterceptors = Collections.unmodifiableList(queryInterceptors);

            List<JdbcInterceptor> reversedList = new LinkedList<>(queryInterceptors);
            Collections.reverse(reversedList);
            this.reverseQueryInterceptors = Collections.unmodifiableList(reversedList);
        }

        if(commandInterceptors ==null){
            this.commandInterceptors = Collections.EMPTY_LIST;
            this.reverseCommandInterceptors = Collections.EMPTY_LIST;
        }else{
            this.commandInterceptors = Collections.unmodifiableList(commandInterceptors);

            List<JdbcInterceptor> reversedList = new LinkedList<>(queryInterceptors);
            Collections.reverse(reversedList);
            this.reverseCommandInterceptors = Collections.unmodifiableList(reversedList);
        }
    }

    @Override
    public JdbcOperations getJdbcOperations() {
        return namedParameterJdbcOperations.getJdbcOperations();
    }

    @Override
    public void query(String sql, SqlParameterSource paramSource, RowCallbackHandler rch) throws DataAccessException {
        namedParameterJdbcOperations.query(convertSql(sql), convertParam(paramSource), new InterceptedRowCallbackHandler(rch, reverseQueryInterceptors));
    }

    @Override
    public void query(String sql, RowCallbackHandler rch) throws DataAccessException {
        namedParameterJdbcOperations.query(convertSql(sql), convertParam(null), new InterceptedRowCallbackHandler(rch, reverseQueryInterceptors));
    }


    @Override
    public void query(String sql, Map<String, ?> paramMap, RowCallbackHandler rch) throws DataAccessException {
        namedParameterJdbcOperations.query(convertSql(sql), convertParam(new MapSqlParameterSource(paramMap)), new InterceptedRowCallbackHandler(rch, reverseQueryInterceptors));
    }

    @Override
    @Nullable
    public <T> T query(String sql, SqlParameterSource paramSource, ResultSetExtractor<T> rse) throws DataAccessException {
        return namedParameterJdbcOperations.query(convertSql(sql), paramSource, new InterceptedResultSetExtractor<>(rse, reverseQueryInterceptors));
    }

    @Override
    @Nullable
    public <T> T query(String sql, Map<String, ?> paramMap, ResultSetExtractor<T> rse) throws DataAccessException {
        return namedParameterJdbcOperations.query(convertSql(sql), convertParam(new MapSqlParameterSource(paramMap)), new InterceptedResultSetExtractor<>(rse, reverseQueryInterceptors));
    }

    @Override
    @Nullable
    public <T> T query(String sql, ResultSetExtractor<T> rse) throws DataAccessException {
        return namedParameterJdbcOperations.query(convertSql(sql), convertParam(null), new InterceptedResultSetExtractor<>(rse, reverseQueryInterceptors));
    }

    @Override
    public <T> List<T> query(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper) throws DataAccessException {
        return namedParameterJdbcOperations.query(convertSql(sql), convertParam(paramSource), new InterceptedRowMapper<>(rowMapper, reverseQueryInterceptors));
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return namedParameterJdbcOperations.query(convertSql(sql), convertParam(null), new InterceptedRowMapper<>(rowMapper, reverseQueryInterceptors));
    }

    @Override
    public <T> List<T> query(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws DataAccessException {
        return namedParameterJdbcOperations.query(convertSql(sql), convertParam(new MapSqlParameterSource(paramMap)), new InterceptedRowMapper<>(rowMapper, reverseQueryInterceptors));
    }

    @Override
    @Nullable
    public <T> T queryForObject(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper) throws DataAccessException {
        return namedParameterJdbcOperations.queryForObject(convertSql(sql), convertParam(paramSource), new InterceptedRowMapper<>(rowMapper, reverseQueryInterceptors));
    }

    @Override
    @Nullable
    public <T> T queryForObject(String sql, SqlParameterSource paramSource, Class<T> requiredType) throws DataAccessException {
        return namedParameterJdbcOperations.queryForObject(convertSql(sql), convertParam(paramSource), requiredType);
    }

    @Override
    @Nullable
    public <T> T queryForObject(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws DataAccessException {
        return namedParameterJdbcOperations.queryForObject(convertSql(sql), convertParam(new MapSqlParameterSource(paramMap)), new InterceptedRowMapper<>(rowMapper, reverseQueryInterceptors));
    }

    @Override
    @Nullable
    public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> requiredType) throws DataAccessException {
        return namedParameterJdbcOperations.queryForObject(convertSql(sql), convertParam(new MapSqlParameterSource(paramMap)), requiredType);
    }

    @Override
    public Map<String, Object> queryForMap(String sql, SqlParameterSource paramSource) throws DataAccessException {
        Map<String, Object> map = namedParameterJdbcOperations.queryForMap(convertSql(sql), convertParam(paramSource));
        for(JdbcInterceptor listener : reverseQueryInterceptors){
            listener.output(map);
        }
        return map;
    }

    @Override
    public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) throws DataAccessException {
        Map<String, Object> map =  namedParameterJdbcOperations.queryForMap(convertSql(sql), convertParam(new MapSqlParameterSource(paramMap)));
        for(JdbcInterceptor listener : reverseQueryInterceptors){
            listener.output(map);
        }
        return map;
    }

    @Override
    public <T> List<T> queryForList(String sql, SqlParameterSource paramSource, Class<T> elementType) throws DataAccessException {
        return namedParameterJdbcOperations.queryForList(convertSql(sql), convertParam(paramSource), elementType);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql, SqlParameterSource paramSource) throws DataAccessException {
        List<Map<String, Object>> list = namedParameterJdbcOperations.queryForList(convertSql(sql), convertParam(paramSource));
        for(JdbcInterceptor listener : reverseQueryInterceptors){
            listener.output(list);
        }
        return list;
    }

    @Override
    public <T> List<T> queryForList(String sql, Map<String, ?> paramMap, Class<T> elementType) throws DataAccessException {
        return namedParameterJdbcOperations.queryForList(convertSql(sql), convertParam(new MapSqlParameterSource(paramMap)), elementType);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) throws DataAccessException {
        List<Map<String, Object>> list =  namedParameterJdbcOperations.queryForList(convertSql(sql), convertParam(new MapSqlParameterSource(paramMap)));
        for(JdbcInterceptor listener : reverseQueryInterceptors){
            listener.output(list);
        }
        return list;
    }

    @Override
    public int update(String sql, SqlParameterSource paramSource) throws DataAccessException {
        int count = namedParameterJdbcOperations.update(convertCommandSql(sql), convertCommandParam(paramSource));
        for(JdbcInterceptor listener : reverseCommandInterceptors){
            listener.output(count);
        }
        return count;
    }

    @Override
    public int update(String sql, Map<String, ?> paramMap) throws DataAccessException {
        int count = namedParameterJdbcOperations.update(convertCommandSql(sql), convertCommandParam(new MapSqlParameterSource(paramMap)));
        for(JdbcInterceptor listener : reverseCommandInterceptors){
            listener.output(count);
        }
        return count;
    }

    @Override
    public int[] batchUpdate(String sql, SqlParameterSource[] batchArgs) {
        return namedParameterJdbcOperations.batchUpdate(convertCommandSql(sql), convertCommandParams(batchArgs));
    }

    @Override
    public int[] batchUpdate(String sql, Map<String, ?>[] batchValues) {
        if(commandInterceptors.size()==0){
            return namedParameterJdbcOperations.batchUpdate(sql, batchValues);
        }

        return namedParameterJdbcOperations.batchUpdate(convertCommandSql(sql), convertCommandParams(batchValues));
    }

    /* Private methods */

    private String convertSql(String sql){
        String convertedSql = sql;
        for(JdbcInterceptor listener : queryInterceptors){
            convertedSql = listener.sql(convertedSql);
        }
        return convertedSql;
    }

    private SqlParameterSource convertParam(SqlParameterSource paramSource){
        if(queryInterceptors.size()==0){
            return paramSource;
        }

        MapSqlParameterSource copiedParam = copy(paramSource);
        for(JdbcInterceptor listener : queryInterceptors){
            listener.param(copiedParam);
        }
        return copiedParam;
    }

    private SqlParameterSource[] convertParams(SqlParameterSource[] batchArgs){
        if(queryInterceptors.size()==0){
            return batchArgs;
        }

        MapSqlParameterSource[] copiedParam = copy(batchArgs);
        for(JdbcInterceptor listener : queryInterceptors){
            listener.param(copiedParam);
        }
        return copiedParam;
    }

    private MapSqlParameterSource[] convertParams(Map<String,?>[] batchArgs){
        MapSqlParameterSource[] copiedParam = copy(batchArgs);
        for(JdbcInterceptor listener : queryInterceptors){
            listener.param(copiedParam);
        }
        return copiedParam;
    }


    private String convertCommandSql(String sql){
        String convertedSql = sql;
        for(JdbcInterceptor listener : commandInterceptors){
            convertedSql = listener.sql(convertedSql);
        }
        return convertedSql;
    }

    private SqlParameterSource convertCommandParam(SqlParameterSource paramSource){
        if(commandInterceptors.size()==0){
            return paramSource;
        }

        MapSqlParameterSource copiedParam = copy(paramSource);
        for(JdbcInterceptor listener : commandInterceptors){
            listener.param(copiedParam);
        }
        return copiedParam;
    }

    private SqlParameterSource[] convertCommandParams(SqlParameterSource[] batchArgs){
        if(commandInterceptors.size()==0){
            return batchArgs;
        }

        MapSqlParameterSource[] copiedParam = copy(batchArgs);
        for(JdbcInterceptor listener : commandInterceptors){
            listener.param(copiedParam);
        }
        return copiedParam;
    }

    private MapSqlParameterSource[] convertCommandParams(Map<String,?>[] batchArgs){
        MapSqlParameterSource[] copiedParam = copy(batchArgs);
        for(JdbcInterceptor listener : commandInterceptors){
            listener.param(copiedParam);
        }
        return copiedParam;
    }

    private MapSqlParameterSource copy(SqlParameterSource paramSource){
        if(paramSource==null){
            return new MapSqlParameterSource();
        }

        MapSqlParameterSource copied = new MapSqlParameterSource();
        for(String key : paramSource.getParameterNames()){
            copied.addValue(key, paramSource.getValue(key));
        }
        return copied;
    }

    private MapSqlParameterSource[] copy(SqlParameterSource[] batchArgs){

        MapSqlParameterSource[] copiedArray = new MapSqlParameterSource[batchArgs.length];
        int index = 0;
        for(SqlParameterSource batchArg : batchArgs){
            copiedArray[index] = copy(batchArg);
            index++;
        }
        return copiedArray;
    }

    private MapSqlParameterSource[] copy(Map<String,?>[] batchArgs){

        MapSqlParameterSource[] copiedArray = new MapSqlParameterSource[batchArgs.length];
        int index = 0;
        for(Map<String,?> batchArg : batchArgs){
            copiedArray[index] = new MapSqlParameterSource(batchArg);
            index++;
        }
        return copiedArray;
    }


    /* Unsupported methods */

    @Override
    public int update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder) throws DataAccessException {
        throw new UnsupportedOperationException("This %s project does not support this feature.".format(PROJECT_NAME));
//        return namedParameterJdbcOperations.update(sql, paramSource, generatedKeyHolder);
    }

    @Override
    public int update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder, String[] keyColumnNames) throws DataAccessException {
        throw new UnsupportedOperationException("This %s project does not support this feature.".format(PROJECT_NAME));
//        return namedParameterJdbcOperations.update(sql, paramSource, generatedKeyHolder, keyColumnNames);
    }

    @Override
    @Nullable
    public <T> T execute(String sql, SqlParameterSource paramSource, PreparedStatementCallback<T> action) throws DataAccessException {
        throw new UnsupportedOperationException("This %s project does not support this feature.".format(PROJECT_NAME));
//        return namedParameterJdbcOperations.execute(sql, paramSource, action);
    }

    @Override
    @Nullable
    public <T> T execute(String sql, Map<String, ?> paramMap, PreparedStatementCallback<T> action) throws DataAccessException {
        throw new UnsupportedOperationException("This %s project does not support this feature.".format(PROJECT_NAME));
//        return namedParameterJdbcOperations.execute(sql, paramMap, action);
    }

    @Override
    @Nullable
    public <T> T execute(String sql, PreparedStatementCallback<T> action) throws DataAccessException {
        throw new UnsupportedOperationException("This %s project does not support this feature.".format(PROJECT_NAME));
//        return namedParameterJdbcOperations.execute(sql, action);
    }


    @Override
    public SqlRowSet queryForRowSet(String sql, SqlParameterSource paramSource) throws DataAccessException {
        throw new UnsupportedOperationException("This %s project does not support this feature.".format(PROJECT_NAME));
//        return namedParameterJdbcOperations.queryForRowSet(sql, paramSource);
    }

    @Override
    public SqlRowSet queryForRowSet(String sql, Map<String, ?> paramMap) throws DataAccessException {
        throw new UnsupportedOperationException("This %s project does not support this feature.".format(PROJECT_NAME));
//        return namedParameterJdbcOperations.queryForRowSet(sql, paramMap);
    }

}
