package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/com/dummy/myerp/business/applicationContext.xml")
public class ComptabiliteManagerImplTest {

    private static final String ECRITURE_TEST_REF = "AC-2020/00001";
    private static final String ECRITURE_TEST_LIBELLE = "libelle-ecriture-test";
    private static final String ECRITURE_TEST_REF_NEW = "AC-2020/00002";
    private static final String ECRITURE_TEST_LIBELLE_NEW = "libelle-ecriture-test-new";
    private static final String LIGNE_TEST_LIBELLE = "libelle-ligne-test";
    private static final String LIGNE_TEST_LIBELLE_NEW = "libelle-ligne-test-new";


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
    }

    @Test
    public void testGetListCompteComptable() {
        Assert.assertEquals(7, manager.getListCompteComptable().size());
    }

    @Test
    public void testGetListJournalComptable() {
        Assert.assertEquals(4, manager.getListJournalComptable().size());
    }

    @Test
    public void testGetListEcritureComptable() {
        Assert.assertEquals(5, manager.getListEcritureComptable().size());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    public void testInsertEcritureComptable() throws FunctionalException {
        manager.insertEcritureComptable(vEcritureComptable);
        Assert.assertNotNull(getEcritureComptableByRef(vEcritureComptable.getReference()));
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    public void testUpdateEcritureComptable() throws FunctionalException {
        testInsertEcritureComptable();
        EcritureComptable ecritureComptable = getEcritureComptableByRef(ECRITURE_TEST_REF);
        ecritureComptable.setReference(ECRITURE_TEST_REF_NEW);
        ecritureComptable.setLibelle(ECRITURE_TEST_LIBELLE_NEW);
        ecritureComptable.setJournal(new JournalComptable("BQ", "Banque"));

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

        Assert.assertNull(getEcritureComptableByRef(ECRITURE_TEST_REF));

        EcritureComptable actualEcritureComptable = getEcritureComptableByRef(ECRITURE_TEST_REF_NEW);
        Assert.assertNotNull(actualEcritureComptable);

        for (LigneEcritureComptable ligne : actualEcritureComptable.getListLigneEcriture()) {
            Assert.assertTrue(ligne.getLibelle().equalsIgnoreCase(LIGNE_TEST_LIBELLE_NEW));
        }
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    public void testDeleteEcritureComptable() throws FunctionalException {
        testInsertEcritureComptable();
        EcritureComptable ecritureComptable = getEcritureComptableByRef(ECRITURE_TEST_REF);
        Assert.assertNotNull(ecritureComptable);

        manager.deleteEcritureComptable(ecritureComptable.getId());

        Assert.assertNull(getEcritureComptableByRef(ECRITURE_TEST_REF));
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
    public void checkEcritureComptableUnitRG5Annee() throws Exception {
        vEcritureComptable.setReference("AC-2019/00001");
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    public void checkEcritureComptableContextRG6Violation() throws Exception {
        testInsertEcritureComptable();
        vEcritureComptable.setId(null);
        manager.checkEcritureComptableContext(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    public void checkEcritureComptableContextRG6ViolationWithId() throws Exception {
        testInsertEcritureComptable();
        vEcritureComptable.setId(999);
        manager.checkEcritureComptableContext(vEcritureComptable);
    }

}
