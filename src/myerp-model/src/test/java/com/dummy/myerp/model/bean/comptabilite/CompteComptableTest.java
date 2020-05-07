package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CompteComptableTest {

    @Test
    public void testCompteComptableGettersAndSetters() {
        CompteComptable compteComptable = new CompteComptable();
        compteComptable.setNumero(1);
        compteComptable.setLibelle("compte de test");

        Assert.assertEquals(java.util.Optional.of(1).get(), compteComptable.getNumero());
        Assert.assertEquals("compte de test", compteComptable.getLibelle());
    }

    @Test
    public void testToString() {
        CompteComptable compteComptable = new CompteComptable(1);
        compteComptable.setLibelle("compte de test");

        String expectedString = String.format(
                "CompteComptable{numero=%d, libelle='%s'}", compteComptable.getNumero(), compteComptable.getLibelle());

        Assert.assertEquals(expectedString, compteComptable.toString());
    }

    @Test
    public void testGetByNumero() {
        List<CompteComptable> compteComptables = new ArrayList<>();
        compteComptables.add(new CompteComptable(1, "premier compte de test"));
        compteComptables.add(new CompteComptable(2, "second compte de test"));
        compteComptables.add(new CompteComptable(3, "troisième compte de test"));
        compteComptables.add(new CompteComptable(4, "quatrième compte de test"));

        Assert.assertEquals("troisième compte de test", CompteComptable.getByNumero(compteComptables, 3).getLibelle());
        Assert.assertEquals("second compte de test", CompteComptable.getByNumero(compteComptables, 2).getLibelle());

    }
}