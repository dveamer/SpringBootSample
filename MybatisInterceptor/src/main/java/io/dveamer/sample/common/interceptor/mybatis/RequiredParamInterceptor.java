package io.dveamer.sample.common.interceptor.mybatis;

import io.dveamer.sample.common.scope.RequestScopeUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;

import java.util.*;
import java.util.stream.Collectors;


@Intercepts({
        @Signature(type=Executor.class, method="update", args={MappedStatement.class, Object.class})
//        ,@Signature(type=Executor.class, method="query", args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class RequiredParamInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object oldParameter = invocation.getArgs()[1];
        System.out.println(oldParameter);

        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        BoundSql bs = ms.getBoundSql(oldParameter);

        invocation.getArgs()[1] = renewParameters(oldParameter, bs);

        return invocation.proceed();
    }


    private Map<String, Object> defaultMap(){
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("createdBy", RequestScopeUtil.getAttribute().getUserId());
        return newMap;
    }

    private Map<String, Object> renewParameters(Object oldParameter, BoundSql bs){

        if(oldParameter==null){
            return defaultMap();
        }

        if(oldParameter instanceof Map){
            Map<String, Object> oldMap = (Map<String, Object>) oldParameter;
            oldMap.putAll(defaultMap());
            return oldMap;
        }

        Set<String> keySet = defaultMap().keySet();
        List<ParameterMapping> pmList = bs.getParameterMappings().stream()
                .filter(p->!keySet.contains(p.getProperty()))
                .collect(Collectors.toList());

        if(pmList.size()==0){
            return defaultMap();
        }

        if(pmList.size()==1){
            Map<String, Object> newMap = defaultMap();
            newMap.put(pmList.get(0).getProperty(), oldParameter);
            return newMap;
        }

        System.out.println(pmList);
        String detailKey = pmList.get(0).getProperty();
        if(detailKey.indexOf(".")>-1){
            String key = detailKey.substring(0, detailKey.indexOf("."));

            Map<String, Object> newMap = defaultMap();
            newMap.put(key, oldParameter);
            return newMap;
        }

        Map<String, Object> newMap = defaultMap();
        newMap.put("default", oldParameter);
        return newMap;
    }



    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


}
