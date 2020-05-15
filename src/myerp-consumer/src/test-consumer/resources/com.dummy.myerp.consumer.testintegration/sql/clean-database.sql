DELETE FROM myerp.ligne_ecriture_comptable WHERE libelle = 'libelle-ligne-test';
DELETE FROM myerp.ligne_ecriture_comptable WHERE libelle = 'libelle-ligne-test-new';
DELETE FROM myerp.ecriture_comptable WHERE libelle = 'libelle-ecriture-test';
DELETE FROM myerp.ecriture_comptable WHERE libelle = 'libelle-ecriture-test-new';
UPDATE myerp.sequence_ecriture_comptable SET derniere_valeur = 40 WHERE journal_code = 'AC' AND annee = 2016
