package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.context.ContextConfiguration;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AbstractBusinessManager.class)
@ContextConfiguration(locations = "/com/dummy/myerp/business/applicationContext.xml")
public class ComptabiliteManagerImplTest {

    private static final String ECRITURE_TEST_REF = "AC-2020/00001";
    private static final String ECRITURE_TEST_LIBELLE = "libelle-ecriture-test";
    private static final String ECRITURE_TEST_REF_NEW = "AC-2020/00002";
    private static final String ECRITURE_TEST_LIBELLE_NEW = "libelle-ecriture-test-new";
    private static final String LIGNE_TEST_LIBELLE = "libelle-ligne-test";
    private static final String LIGNE_TEST_LIBELLE_NEW = "libelle-ligne-test-new";

    @Mock
    private DaoProxy daoProxy;
    @Mock
    private ComptabiliteDao comptabiliteDao;
    @Mock
    private TransactionManager transactionManager;
    @Mock
    private Validator validator;
    @InjectMocks
    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    private EcritureComptable vEcritureComptable = new EcritureComptable();

    @Before
    public void setup() {
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle(ECRITURE_TEST_LIBELLE);
        vEcritureComptable.setReference(ECRITURE_TEST_REF);
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                LIGNE_TEST_LIBELLE, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                LIGNE_TEST_LIBELLE, null,
                new BigDecimal(123)));

        PowerMockito.mockStatic(AbstractBusinessManager.class);
        Mockito.when(AbstractBusinessManager.getDaoProxy()).thenReturn(daoProxy);
        Mockito.when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
        Mockito.when(AbstractBusinessManager.getTransactionManager()).thenReturn(transactionManager);
        Mockito.when(AbstractBusinessManager.getConstraintValidator()).thenReturn(validator);
    }

    @Test
    public void testGetListCompteComptable() {
        ArrayList<CompteComptable> comptes = new ArrayList();
        comptes.add(new CompteComptable(1, "compte test"));
        Mockito.when(comptabiliteDao.getListCompteComptable()).thenReturn(comptes);
        Assert.assertEquals(1, manager.getListCompteComptable().size());
        Assert.assertEquals("compte test", manager.getListCompteComptable().get(0).getLibelle());
        Assert.assertEquals(java.util.Optional.of(1).get(), manager.getListCompteComptable().get(0).getNumero());
    }

    @Test
    public void testGetListJournalComptable() {
        ArrayList<JournalComptable> journalComptables = new ArrayList();
        journalComptables.add(new JournalComptable("OC", "journal test"));
        Mockito.when(comptabiliteDao.getListJournalComptable()).thenReturn(journalComptables);
        Assert.assertEquals(1, manager.getListJournalComptable().size());
        Assert.assertEquals("journal test", manager.getListJournalComptable().get(0).getLibelle());
        Assert.assertEquals("OC", manager.getListJournalComptable().get(0).getCode());
    }

    @Test
    public void testGetListEcritureComptable() {
        ArrayList<EcritureComptable> ecritureComptables = new ArrayList();
        ecritureComptables.add(new EcritureComptable());
        Mockito.when(comptabiliteDao.getListEcritureComptable()).thenReturn(ecritureComptables);
        Assert.assertEquals(1, manager.getListEcritureComptable().size());
    }

    @Test
    public void testInsertEcritureComptable() throws FunctionalException, NotFoundException {
        Mockito.when(comptabiliteDao.getEcritureComptableByRef(any())).thenThrow(NotFoundException.class);
        manager.insertEcritureComptable(vEcritureComptable);
        Mockito.verify(comptabiliteDao).insertEcritureComptable(any());
    }

    @Test
    public void testUpdateEcritureComptable() throws FunctionalException, NotFoundException {
        Mockito.when(comptabiliteDao.getEcritureComptableByRef(any())).thenThrow(NotFoundException.class);
        ArrayList<EcritureComptable> ecritureComptables = new ArrayList<>();
        ecritureComptables.add(vEcritureComptable);
        Mockito.when(manager.getListEcritureComptable()).thenReturn(ecritureComptables);

        EcritureComptable ecritureComptable = getEcritureComptableByRef(ECRITURE_TEST_REF);
        ecritureComptable.setReference(ECRITURE_TEST_REF_NEW);
        ecritureComptable.setLibelle(ECRITURE_TEST_LIBELLE_NEW);
        ecritureComptable.setDate(Date.from(Instant.parse("2020-11-30T18:35:24.00Z")));
        ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));

        List<LigneEcritureComptable> lignes = ecritureComptable.getListLigneEcriture();
        List<LigneEcritureComptable> updatedLignes = new ArrayList<>();
        for (LigneEcritureComptable ligne : lignes) {
            LigneEcritureComptable updatedLigne = ligne;
            updatedLigne.setLibelle(LIGNE_TEST_LIBELLE_NEW);
            updatedLignes.add(updatedLigne);
        }

        ecritureComptable.getListLigneEcriture().clear();
        for (LigneEcritureComptable ligneToAdd : updatedLignes) {
            ecritureComptable.getListLigneEcriture().add(ligneToAdd);
        }

        manager.updateEcritureComptable(ecritureComptable);
        Mockito.verify(comptabiliteDao).updateEcritureComptable(any());

    }

    @Test
    public void testDeleteEcritureComptable() {
        manager.deleteEcritureComptable(vEcritureComptable.getId());
        Mockito.verify(comptabiliteDao).deleteEcritureComptable(any());
    }

    private EcritureComptable getEcritureComptableByRef(String reference) {
        for (EcritureComptable ecriture : manager.getListEcritureComptable()) {
            if (ecriture.getReference().equalsIgnoreCase(reference)) {
                return ecriture;
            }
        }
        return null;
    }

    @Test
    public void checkEcritureComptableUnit() throws Exception {
        Mockito.when(comptabiliteDao.getEcritureComptableByRef(any())).thenThrow(NotFoundException.class);
        manager.checkEcritureComptable(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable ecritureComptable;
        ecritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(ecritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {
        vEcritureComptable.getListLigneEcriture().clear();
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(-123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5Journal() throws Exception {
        vEcritureComptable.setReference("BQ-2020/00001");
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG7() throws Exception {
        vEcritureComptable.getListLigneEcriture().clear();
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(String.valueOf(123.123)),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(String.valueOf(123.123))));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5Annee() throws Exception {
        vEcritureComptable.setReference("AC-2019/00001");
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableContextRG6Violation() throws Exception {
        EcritureComptable olderEcritureComptable = vEcritureComptable;
        olderEcritureComptable.setId(999);
        Mockito.when(comptabiliteDao.getEcritureComptableByRef(any())).thenReturn(olderEcritureComptable);

        vEcritureComptable.setId(null);
        manager.checkEcritureComptableContext(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableContextRG6ViolationWithId() throws Exception {
        EcritureComptable olderEcritureComptable = new EcritureComptable();
        olderEcritureComptable.setId(998);
        olderEcritureComptable.setReference(ECRITURE_TEST_REF);
        Mockito.when(comptabiliteDao.getEcritureComptableByRef(any())).thenReturn(olderEcritureComptable);
        vEcritureComptable.setId(999);
        manager.checkEcritureComptableContext(vEcritureComptable);
    }

    @Test
    public void testAddReference() throws Exception {
        Mockito.when(comptabiliteDao.getEcritureComptableByRef(any())).thenThrow(NotFoundException.class);
        Mockito.when(comptabiliteDao.getSequenceEcritureComptable(any(), anyInt()))
                .thenReturn(new SequenceEcritureComptable("OC", 2020, 40));
        vEcritureComptable.setReference(null);
        vEcritureComptable.setJournal(new JournalComptable("OC", "journal test"));
        vEcritureComptable.setDate(Date.from(Instant.parse("2020-12-31T18:35:24.00Z")));
        manager.addReference(vEcritureComptable);
        manager.checkEcritureComptable(vEcritureComptable);
        Assert.assertEquals("OC-2020/00041", vEcritureComptable.getReference());
    }

}
