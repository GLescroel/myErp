package com.dummy.myerp.technical.util.spring;

import org.junit.Assert;
import org.junit.Test;

public class NullFactoryBeanTest {

    @Test
    public void testNullFactoryBean() throws Exception {
        NullFactoryBean nullFactoryBean = new NullFactoryBean(String.class);

        Assert.assertEquals(String.class, nullFactoryBean.getObjectType());
        Assert.assertEquals(false, nullFactoryBean.isSingleton());
        Assert.assertNull(nullFactoryBean.getObject());
    }
}