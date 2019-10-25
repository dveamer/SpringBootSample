package io.dveamer.sample.config;

import io.dveamer.sample.common.interceptor.jdbc.CudModifierJdbcInterceptor;
import io.dveamer.sample.common.interceptor.jdbc.JdbcInterceptor;
import io.dveamer.sample.common.interceptor.jdbc.PrintJdbcInterceptor;
import io.dveamer.sample.common.interceptor.jdbc.SqlIdJdbcInterceptor;
import io.dveamer.sample.common.wrapper.jdbc.InterceptedNamedParameterJdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class DatabaseConfig {

    @Bean
    public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource){

        JdbcInterceptor sqlIdInterceptor = new SqlIdJdbcInterceptor();
        JdbcInterceptor printInterceptor = new PrintJdbcInterceptor();

        List<JdbcInterceptor> queryInterceptors = new ArrayList<>();
        queryInterceptors.add(sqlIdInterceptor);
        queryInterceptors.add(printInterceptor);

        List<JdbcInterceptor> commandInterceptors = new ArrayList<>();
        commandInterceptors.add(sqlIdInterceptor);
        commandInterceptors.add(new CudModifierJdbcInterceptor());
        commandInterceptors.add(printInterceptor);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setFetchSize(300);
        return new InterceptedNamedParameterJdbcTemplate(new NamedParameterJdbcTemplate(jdbcTemplate), queryInterceptors, commandInterceptors);
    }

}
