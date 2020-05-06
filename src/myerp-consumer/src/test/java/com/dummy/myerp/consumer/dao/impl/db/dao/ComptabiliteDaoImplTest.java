package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ComptabiliteDaoImplTest {

    private static final String ECRITURE_TEST_REF = "ref-ecriture-test";
    private static final String ECRITURE_TEST_LIBELLE = "libelle-ecriture-test";
    private static final String ECRITURE_TEST_REF_NEW = "ref-ecriture-test-new";
    private static final String ECRITURE_TEST_LIBELLE_NEW = "libelle-ecriture-test-new";
    private static final String LIGNE_TEST_LIBELLE = "libelle-ligne-test";
    private static final String LIGNE_TEST_LIBELLE_NEW = "libelle-ligne-test-new";
    
    private static final String ECRITURE_REFERENCE_ID_MOINS1 = "AC-2016/00001";
    private static final String ECRITURE_LIBELLE_ID_MOINS1 = "Cartouches d’imprimante";
    private static final String ECRITURE_CODE_JOURNAL_ID_MOINS1 = "AC";
    private static final String ECRITURE_DATE_ID_MOINS1 = "2016-12-31";

    @Autowired
    ComptabiliteDaoImpl comptabiliteDao;

    @Test
    public void getListCompteComptable() {
        Assert.assertEquals(7, comptabiliteDao.getListCompteComptable().size());
    }

    @Test
    public void getListJournalComptable() {
        Assert.assertEquals(4, comptabiliteDao.getListJournalComptable().size());
    }

    @Test
    public void getListEcritureComptable() {
        Assert.assertEquals(5, comptabiliteDao.getListEcritureComptable().size());
    }

    @Test(expected = NotFoundException.class)
    public void testGetEcritureComptableReturnNotFound() throws NotFoundException {
        comptabiliteDao.getEcritureComptable(9999);
    }

    @Test
    public void getEcritureComptable() throws NotFoundException {
        Assert.assertEquals(ECRITURE_REFERENCE_ID_MOINS1, comptabiliteDao.getEcritureComptable(-1).getReference());
        Assert.assertEquals(ECRITURE_LIBELLE_ID_MOINS1, comptabiliteDao.getEcritureComptable(-1).getLibelle());
        Assert.assertEquals(ECRITURE_CODE_JOURNAL_ID_MOINS1, comptabiliteDao.getEcritureComptable(-1).getJournal().getCode());
        Assert.assertEquals(ECRITURE_DATE_ID_MOINS1, comptabiliteDao.getEcritureComptable(-1).getDate().toString());
    }

    @Test
    public void getEcritureComptableByRef() throws NotFoundException {
        Assert.assertEquals(ECRITURE_LIBELLE_ID_MOINS1, comptabiliteDao.getEcritureComptableByRef(ECRITURE_REFERENCE_ID_MOINS1).getLibelle());
        Assert.assertEquals(ECRITURE_DATE_ID_MOINS1, comptabiliteDao.getEcritureComptableByRef(ECRITURE_REFERENCE_ID_MOINS1).getDate().toString());
        Assert.assertEquals((long) -1, (long) comptabiliteDao.getEcritureComptableByRef(ECRITURE_REFERENCE_ID_MOINS1).getId());
        Assert.assertEquals(ECRITURE_CODE_JOURNAL_ID_MOINS1, comptabiliteDao.getEcritureComptableByRef(ECRITURE_REFERENCE_ID_MOINS1).getJournal().getCode());
    }

    @Test(expected = NotFoundException.class)
    public void testGetEcritureComptableByRefReturnNotFound() throws NotFoundException {
        comptabiliteDao.getEcritureComptableByRef("9999");
    }

    @Test
    public void loadListLigneEcriture() {
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(-1);
        comptabiliteDao.loadListLigneEcriture(ecritureComptable);
        Assert.assertTrue(!ecritureComptable.getListLigneEcriture().isEmpty());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    public void insertEcritureComptable() throws NotFoundException {
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setReference(ECRITURE_TEST_REF);
        ecritureComptable.setLibelle(ECRITURE_TEST_LIBELLE);
        JournalComptable journal = new JournalComptable();
        journal.setCode("AC");
        journal.setLibelle("Achat");
        ecritureComptable.setJournal(journal);
        ecritureComptable.setDate(Date.valueOf(LocalDate.now()));
        comptabiliteDao.insertEcritureComptable(ecritureComptable);

        Assert.assertNotNull(comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF).getId());
        Assert.assertEquals(ECRITURE_TEST_LIBELLE, comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF).getLibelle());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    public void insertListLigneEcritureComptable() throws NotFoundException {
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setReference(ECRITURE_TEST_REF);
        ecritureComptable.setLibelle(ECRITURE_TEST_LIBELLE);
        ecritureComptable.setDate(Date.valueOf(LocalDate.now()));
        ecritureComptable.setJournal(comptabiliteDao.getListJournalComptable().get(0));
        comptabiliteDao.insertEcritureComptable(ecritureComptable);

        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
        CompteComptable compteComptable = new CompteComptable();
        compteComptable.setNumero(401);
        ligneEcritureComptable.setCompteComptable(compteComptable);
        ligneEcritureComptable.setCredit(BigDecimal.valueOf(99.9));
        ligneEcritureComptable.setDebit(BigDecimal.valueOf(99.9));
        ligneEcritureComptable.setLibelle(LIGNE_TEST_LIBELLE);

        ecritureComptable.getListLigneEcriture().add(ligneEcritureComptable);
        comptabiliteDao.insertListLigneEcritureComptable(ecritureComptable);

        boolean ligneFound = false;
            for (LigneEcritureComptable ligne : comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF).getListLigneEcriture()) {
                if (ligne.getLibelle().contains(ligneEcritureComptable.getLibelle())) {
                    ligneFound = true;
                }
            }
        Assert.assertTrue(ligneFound);
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    public void updateEcritureComptable() throws NotFoundException {
        insertListLigneEcritureComptable();
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF);
        ecritureComptable.setDate(Date.valueOf(LocalDate.now().plusDays(1)));
        ecritureComptable.setLibelle(ECRITURE_TEST_LIBELLE_NEW);
        ecritureComptable.setReference(ECRITURE_TEST_REF_NEW);
        JournalComptable journalComptable = new JournalComptable();
        journalComptable.setCode("BQ");
        ecritureComptable.setJournal(journalComptable);

        LigneEcritureComptable ligne = new LigneEcritureComptable();
        ligne.setLibelle(LIGNE_TEST_LIBELLE_NEW);
        ligne.setDebit(BigDecimal.valueOf(123));
        ligne.setCredit(BigDecimal.valueOf(123));
        CompteComptable compteComptable = new CompteComptable();
        compteComptable.setNumero(512);
        ligne.setCompteComptable(compteComptable);

        comptabiliteDao.updateEcritureComptable(ecritureComptable);

        Assert.assertEquals(ecritureComptable.getDate(), comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF_NEW).getDate());
        Assert.assertEquals(ecritureComptable.getId(), comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF_NEW).getId());
        Assert.assertEquals(ecritureComptable.getJournal().getCode(), comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF_NEW).getJournal().getCode());
        Assert.assertEquals(ecritureComptable.getLibelle(), comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF_NEW).getLibelle());
        Assert.assertEquals(ecritureComptable.getListLigneEcriture().get(0).getCredit(), comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF_NEW).getListLigneEcriture().get(0).getCredit());
        Assert.assertEquals(ecritureComptable.getListLigneEcriture().get(0).getDebit(), comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF_NEW).getListLigneEcriture().get(0).getDebit());
        Assert.assertEquals(ecritureComptable.getListLigneEcriture().get(0).getLibelle(), comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF_NEW).getListLigneEcriture().get(0).getLibelle());
        Assert.assertEquals(ecritureComptable.getListLigneEcriture().get(0).getCompteComptable().getNumero(), comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF_NEW).getListLigneEcriture().get(0).getCompteComptable().getNumero());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    public void deleteEcritureComptable() throws NotFoundException {
        insertEcritureComptable();
        comptabiliteDao.deleteEcritureComptable(comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF).getId());

        boolean ecritureComptableFound = false;
        for (EcritureComptable ecriture : comptabiliteDao.getListEcritureComptable()) {
            if (ecriture.getReference().contains(ECRITURE_TEST_REF)) {
                ecritureComptableFound = true;
            }
        }
        Assert.assertFalse(ecritureComptableFound);
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    public void deleteListLigneEcritureComptable() throws NotFoundException {
        insertListLigneEcritureComptable();
        comptabiliteDao.deleteListLigneEcritureComptable(comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF).getId());

        boolean ligneFound = false;
        for (LigneEcritureComptable ligne : comptabiliteDao.getEcritureComptableByRef(ECRITURE_TEST_REF).getListLigneEcriture()) {
            if (ligne.getLibelle().contains(LIGNE_TEST_LIBELLE)) {
                ligneFound = true;
            }
        }
        Assert.assertFalse(ligneFound);
    }

    @Test
    public void testGetSequenceComptable() throws NotFoundException {
        Assert.assertEquals(java.util.Optional.of(40).get(), comptabiliteDao.getSequenceEcritureComptable("AC", 2016).getDerniereValeur());
    }

    @Test(expected = NotFoundException.class)
    public void testGetSequenceComptableNotFound() throws NotFoundException {
        comptabiliteDao.getSequenceEcritureComptable("AC", 2040);
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean-database.sql")
    public void testSetSequenceComptable() throws NotFoundException {
        comptabiliteDao.setSequenceEcritureComptable("AC", 2016, 99);
        Assert.assertEquals(java.util.Optional.of(99).get(), comptabiliteDao.getSequenceEcritureComptable("AC", 2016).getDerniereValeur());
    }

}