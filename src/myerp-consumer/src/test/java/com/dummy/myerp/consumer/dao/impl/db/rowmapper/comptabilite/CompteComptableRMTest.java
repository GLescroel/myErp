package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
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
public class CompteComptableRMTest {

    @Mock
    private ResultSet resultSet;
    @InjectMocks
    CompteComptableRM compteComptableRM;

    @Test
    public void testShouldMapCompteComptableRow() throws SQLException {

        doReturn(99).when(resultSet).getInt("numero");
        doReturn("compte test").when(resultSet).getString("libelle");

        CompteComptable compteComptable = compteComptableRM.mapRow(resultSet, 1);
        Assert.assertEquals(java.util.Optional.of(99).get(), compteComptable.getNumero());
        Assert.assertEquals("compte test", compteComptable.getLibelle());
    }
}