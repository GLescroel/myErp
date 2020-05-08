package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class JournalComptableRMTest {

    @Mock
    private ResultSet resultSet;
    @InjectMocks
    JournalComptableRM journalComptableRM;

    @Test
    public void testShouldMapJournalComptableRow() throws SQLException {

        doReturn("AC").when(resultSet).getString("code");
        doReturn("journal test").when(resultSet).getString("libelle");

        JournalComptable journalComptable = journalComptableRM.mapRow(resultSet, 1);
        Assert.assertEquals("AC", journalComptable.getCode());
        Assert.assertEquals("journal test", journalComptable.getLibelle());
    }

}