package io.dveamer.sample.requestscope;

import io.dveamer.sample.SpyRequestScope;
import io.dveamer.sample.common.scope.Attribute;
import io.dveamer.sample.common.scope.RequestScope;
import io.dveamer.sample.common.scope.RequestScopeUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RequestScopeTest {

    @BeforeAll
    public void setUp(){
        RequestScopeUtil.defineScope(new SpyRequestScope());
        Attribute attribute = new Attribute();
        attribute.setUserId("100");
        RequestScopeUtil.setAttribute(attribute);
    }

    @Test
    public void getUserId_success() {
        Attribute attribute = RequestScopeUtil.getAttribute();
        assertThat(attribute.getUserId(), equalTo("100"));
    }


    @Test
    public void unmodifiableScopeStore_success() {
        Attribute attribute = RequestScopeUtil.getAttribute();
        RequestScopeUtil.defineScope(new RequestScope());

        assertThat(new Attribute(), not(new Attribute()));
        assertThat(RequestScopeUtil.getAttribute(), equalTo(attribute));
    }


}
