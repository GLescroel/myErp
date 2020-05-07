package com.dummy.myerp.model.bean.comptabilite;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;


public class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                vLibelle,
                vDebit, vCredit);
        return vRetour;
    }

    @Test
    public void testExcritureComptable() {
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(1);
        ecritureComptable.setReference("reference de test");
        ecritureComptable.setLibelle("libelle de test");
        ecritureComptable.setJournal(new JournalComptable("CT", "compte test"));
        ecritureComptable.setDate(Date.valueOf(LocalDate.now()));

        Assert.assertEquals(java.util.Optional.of(1).get(), ecritureComptable.getId());
        Assert.assertEquals("reference de test", ecritureComptable.getReference());
        Assert.assertEquals("libelle de test", ecritureComptable.getLibelle());
        Assert.assertEquals("CT", ecritureComptable.getJournal().getCode());
        Assert.assertEquals("compte test", ecritureComptable.getJournal().getLibelle());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), ecritureComptable.getDate());

    }

    @Test
    public void testGetTotalDebit() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("getTotalDebit");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assert.assertEquals("341.00", String.valueOf(vEcriture.getTotalDebit()));
    }

    @Test
    public void testGetTotalCrebit() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("getTotalDebit");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assert.assertEquals("341.00", String.valueOf(vEcriture.getTotalCredit()));
    }

    @Test
    public void isEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
    }

    @Test
    public void testToString() {
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(1);
        ecritureComptable.setReference("reference de test");
        ecritureComptable.setLibelle("libelle de test");
        ecritureComptable.setJournal(new JournalComptable("CT", "compte test"));
        ecritureComptable.setDate(Date.valueOf(LocalDate.now()));

        String expectedString = String.format("EcritureComptable{id=%d, journal=%s, reference='%s', date=%s, libelle='%s', totalDebit=%s, totalCredit=%s, listLigneEcriture=[\n%s\n]}",
                ecritureComptable.getId(),
                ecritureComptable.getJournal(),
                ecritureComptable.getReference(),
                ecritureComptable.getDate(),
                ecritureComptable.getLibelle(),
                new DecimalFormat("#0.00").format(ecritureComptable.getTotalDebit()).replace(",", "."),
                new DecimalFormat("#0.00").format(ecritureComptable.getTotalCredit()).replace(",", "."),
                StringUtils.join(ecritureComptable.getListLigneEcriture(), "\n"));

        Assert.assertEquals(expectedString, ecritureComptable.toString());

    }
}
