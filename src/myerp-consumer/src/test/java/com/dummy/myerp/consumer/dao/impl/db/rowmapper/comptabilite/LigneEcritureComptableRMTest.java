package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.cache.CompteComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConsumerHelper.class)
public class LigneEcritureComptableRMTest {

    @Mock
    private ResultSet resultSet;

    @Mock
    DaoProxy daoProxy;
    @Mock
    ComptabiliteDao comptabiliteDao;
    @Mock
    CompteComptableDaoCache compteComptableDaoCache;

    @InjectMocks
    LigneEcritureComptableRM ligneEcritureComptableRM;

    @Test
    public void testShouldMapLigneEcritureComptableRow() throws SQLException {

        PowerMockito.mockStatic(ConsumerHelper.class);
        doReturn(99).when(resultSet).getObject("compte_comptable_numero", Integer.class);
        when(ConsumerHelper.getDaoProxy()).thenReturn(daoProxy);
        when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
        CompteComptable compteComptable = new CompteComptable(99, "Compte");
        List<CompteComptable> compteComptables = new ArrayList<>();
        compteComptables.add(compteComptable);
        PowerMockito.doReturn(compteComptables).when(comptabiliteDao).getListCompteComptable();
        PowerMockito.doReturn(compteComptable).when(compteComptableDaoCache).getByNumero(anyInt());

        doReturn("libelle test").when(resultSet).getString("libelle");
        doReturn(BigDecimal.valueOf(40.00)).when(resultSet).getBigDecimal("credit");
        doReturn(BigDecimal.valueOf(50.00)).when(resultSet).getBigDecimal("debit");

        LigneEcritureComptable ligneEcritureComptable = ligneEcritureComptableRM.mapRow(resultSet, 1);
        Assert.assertEquals("libelle test", ligneEcritureComptable.getLibelle());
        Assert.assertEquals(java.util.Optional.of(99).get(), ligneEcritureComptable.getCompteComptable().getNumero());
        Assert.assertEquals("Compte", ligneEcritureComptable.getCompteComptable().getLibelle());
        Assert.assertEquals(BigDecimal.valueOf(40.0), ligneEcritureComptable.getCredit());
        Assert.assertEquals(BigDecimal.valueOf(50.0), ligneEcritureComptable.getDebit());
    }

}