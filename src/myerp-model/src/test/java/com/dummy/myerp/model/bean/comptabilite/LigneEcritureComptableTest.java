package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class LigneEcritureComptableTest {

    @Test
    public void testGettersAndSetters() {
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
        ligneEcritureComptable.setLibelle("ligne test");
        ligneEcritureComptable.setCompteComptable(new CompteComptable(99, "compte test"));
        ligneEcritureComptable.setCredit(BigDecimal.valueOf(100.00));
        ligneEcritureComptable.setDebit(BigDecimal.valueOf(200.00));

        Assert.assertEquals("ligne test", ligneEcritureComptable.getLibelle());
        Assert.assertEquals(java.util.Optional.of(99).get(), ligneEcritureComptable.getCompteComptable().getNumero());
        Assert.assertEquals("compte test", ligneEcritureComptable.getCompteComptable().getLibelle());
        Assert.assertEquals(String.valueOf(200.00), String.valueOf(ligneEcritureComptable.getDebit()));
        Assert.assertEquals(String.valueOf(100.00), String.valueOf(ligneEcritureComptable.getCredit()));
    }

    @Test
    public void testToString() {
        CompteComptable compteComptable = new CompteComptable(99, "compte test");
        LigneEcritureComptable ligne = new LigneEcritureComptable(compteComptable,
                "ligne test", BigDecimal.valueOf(100), BigDecimal.valueOf(200));

        String expectedString = String.format(
                "LigneEcritureComptable{compteComptable=%s, libelle='%s', debit=%s, credit=%s}",
                        compteComptable,
                        ligne.getLibelle(),
                        ligne.getDebit(),
                        ligne.getCredit()
        );

        Assert.assertEquals(expectedString, ligne.toString());
    }

}