package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class SequenceEcritureComptableRMTest {

    @Mock
    private ResultSet resultSet;
    @InjectMocks
    SequenceEcritureComptableRM sequenceEcritureComptableRM;

    @Test
    public void testShouldMapSequenceEcritureComptableRow() throws SQLException {

        doReturn("TU").when(resultSet).getString("journal_code");
        doReturn(2020).when(resultSet).getInt("annee");
        doReturn(99).when(resultSet).getInt("derniere_valeur");

        SequenceEcritureComptable sequenceEcritureComptable = sequenceEcritureComptableRM.mapRow(resultSet, 1);
        Assert.assertEquals("TU", sequenceEcritureComptable.getJournalCode());
        Assert.assertEquals(java.util.Optional.of(2020).get(), sequenceEcritureComptable.getAnnee());
        Assert.assertEquals(java.util.Optional.of(99).get(), sequenceEcritureComptable.getDerniereValeur());

    }
}