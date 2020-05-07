package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JournalComptableTest {

    @Test
    public void testGettersAndSetters() {
        JournalComptable journalComptable = new JournalComptable();
        journalComptable.setCode("TU");
        journalComptable.setLibelle("compte test");

        Assert.assertEquals("TU", journalComptable.getCode());
        Assert.assertEquals("compte test", journalComptable.getLibelle());
    }

    @Test
    public void testToString() {
        JournalComptable journalComptable = new JournalComptable("TU", "compte test");

        String expectedString = String.format("JournalComptable{code='%s', libelle='%s'}",
                journalComptable.getCode(), journalComptable.getLibelle());
        Assert.assertEquals(expectedString, journalComptable.toString());
    }

    @Test
    public void testGetByCode() {
        List<JournalComptable> journalComptables = new ArrayList<>();
        journalComptables.add(new JournalComptable("TU1", "premier journal comptable"));
        journalComptables.add(new JournalComptable("TU2", "second journal comptable"));
        journalComptables.add(new JournalComptable("TU3", "troisième journal comptable"));
        journalComptables.add(new JournalComptable("TU4", "quatrième journal comptable"));

        Assert.assertEquals("troisième journal comptable", JournalComptable.getByCode(journalComptables, "TU3").getLibelle());
        Assert.assertEquals("second journal comptable", JournalComptable.getByCode(journalComptables, "TU2").getLibelle());
    }
}