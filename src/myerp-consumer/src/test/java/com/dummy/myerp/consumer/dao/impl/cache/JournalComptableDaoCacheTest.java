package com.dummy.myerp.consumer.dao.impl.cache;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JournalComptableDaoCacheTest {

    @Test
    public void getByCode() {
        JournalComptableDaoCache journalComptableDaoCache = new JournalComptableDaoCache();
        Assert.assertEquals("Opérations Diverses", journalComptableDaoCache.getByCode("OD").getLibelle());

    }
}