package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SequenceEcritureComptableTest {

    @Test
    public void testSequenceEcritureComptable() {

        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable("AC", 2020, 999);
        Assert.assertEquals("AC", sequenceEcritureComptable.getJournalCode());
        Assert.assertEquals(java.util.Optional.of(2020).get(), sequenceEcritureComptable.getAnnee());
        Assert.assertEquals(java.util.Optional.of(999).get(), sequenceEcritureComptable.getDerniereValeur());

        String expectedString = String.format("SequenceEcritureComptable{journal=%s, annee=%d, derniereValeur=%d}",
                sequenceEcritureComptable.getJournalCode(),
                sequenceEcritureComptable.getAnnee(),
                sequenceEcritureComptable.getDerniereValeur());
        Assert.assertEquals(expectedString, sequenceEcritureComptable.toString());
    }

}