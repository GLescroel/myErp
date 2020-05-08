package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest(ConsumerHelper.class)
public class EcritureComptableRMTest {

    @Mock
    private ResultSet resultSet;

    @Mock
    DaoProxy daoProxy;
    @Mock
    ComptabiliteDao comptabiliteDao;
    @Mock
    JournalComptableDaoCache journalComptableDaoCache;

    @InjectMocks
    EcritureComptableRM ecritureComptableRM;

    @Test
    public void testShouldMapEcritureComptableRow() throws SQLException {

        PowerMockito.mockStatic(ConsumerHelper.class);
        doReturn(99).when(resultSet).getInt("id");
        doReturn("reference test").when(resultSet).getString("reference");
        Date date = Date.valueOf(LocalDate.now());
        doReturn(date).when(resultSet).getDate("date");
        doReturn("libelle test").when(resultSet).getString("libelle");

        doReturn("AC").when(resultSet).getString("journal_code");
        JournalComptable journalComptable = new JournalComptable("AC", "Achat");
        List<JournalComptable> journalComptables = new ArrayList<>();
        journalComptables.add(journalComptable);
        doReturn(journalComptables).when(comptabiliteDao).getListJournalComptable();
        doReturn(journalComptable).when(journalComptableDaoCache).getByCode(anyString());

        when(ConsumerHelper.getDaoProxy()).thenReturn(daoProxy);
        when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
        EcritureComptable ecritureComptable = ecritureComptableRM.mapRow(resultSet, 1);
        Assert.assertEquals(java.util.Optional.of(99).get(), ecritureComptable.getId());
        Assert.assertEquals("libelle test", ecritureComptable.getLibelle());
        Assert.assertEquals("reference test", ecritureComptable.getReference());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), ecritureComptable.getDate());
        Assert.assertEquals("AC", ecritureComptable.getJournal().getCode());
        Assert.assertEquals("Achat", ecritureComptable.getJournal().getLibelle());
    }

}