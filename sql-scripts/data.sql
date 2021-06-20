USE app;
INSERT INTO tische(name) VALUES ("TISCH A");
INSERT INTO tische(name) VALUES ("TISCH B");
INSERT INTO tische(name) VALUES ("TISCH C");


INSERT INTO stati(bezeichnung) VALUES ("BESTELLT");
INSERT INTO stati(bezeichnung) VALUES ("VORBEREITET");
INSERT INTO stati(bezeichnung) VALUES ("GELIEFERT");
INSERT INTO stati(bezeichnung) VALUES ("AUFGETRUNKEN");
INSERT INTO stati(bezeichnung) VALUES ("ABGEBROCHEN");

INSERT INTO getraenke(name, groesse, preis, alkoholgehalt) VALUES ("COLA", 0500, 2.50, 0.0);
INSERT INTO getraenke(name, groesse, preis, alkoholgehalt) VALUES ("FANTA", 0500, 2.50, 0.0);
INSERT INTO getraenke(name, groesse, preis, alkoholgehalt) VALUES ("SPRITE", 0500, 2.50, 0.0);
INSERT INTO getraenke(name, groesse, preis, alkoholgehalt) VALUES ("WEIZEN", 0500, 2.50, 5.1);
INSERT INTO getraenke(name, groesse, preis, alkoholgehalt) VALUES ("PILS", 0500, 2.50, 5.1);
INSERT INTO getraenke(name, groesse, preis, alkoholgehalt) VALUES ("ROTWEIN", 0250, 4.50, 13.5);
INSERT INTO getraenke(name, groesse, preis, alkoholgehalt) VALUES ("WEINSCHORLE", 0250, 2.50, 6.0);

INSERT INTO glaeser(name, leergewicht) VALUES ("ROTWEIN_GLAS", 100);
INSERT INTO glaeser(name, leergewicht) VALUES ("WEISSWEIN_GLAS", 100);
INSERT INTO glaeser(name, leergewicht) VALUES ("WEIZEN_GLAS", 100);
INSERT INTO glaeser(name, leergewicht) VALUES ("PILS_GLAS", 100);
INSERT INTO glaeser(name, leergewicht) VALUES ("SCHORLE_GLAS", 100);
INSERT INTO glaeser(name, leergewicht) VALUES ("500_ML_GLAS", 100);

INSERT INTO kunden(name) VALUES ("Alice");
INSERT INTO kunden(name) VALUES ("Bob");
INSERT INTO kunden(name) VALUES ("Charlie");
INSERT INTO kunden(name) VALUES ("Kim");

INSERT INTO plaetze(name, tisch_id) VALUES ("PLATZ A",1);
INSERT INTO plaetze(name, tisch_id) VALUES ("PLATZ B",1);

INSERT INTO plaetze(name, tisch_id) VALUES ("PLATZ C",2);
INSERT INTO plaetze(name, tisch_id) VALUES ("PLATZ D",2);
INSERT INTO plaetze(name, tisch_id) VALUES ("PLATZ E",2);
INSERT INTO plaetze(name, tisch_id) VALUES ("PLATZ F",2);

INSERT INTO plaetze(name, tisch_id) VALUES ("PLATZ G",3);
INSERT INTO plaetze(name, tisch_id) VALUES ("PLATZ H",3);

INSERT INTO bestellungen(
    zeitpunkt_bestellt,
    zeitpunkt_vorbereitet,
    zeitpunkt_geliefert,
    zeitpunkt_aufgetrunken,
    initialgewicht,
    platz_id,
    getraenk_id,
    glas_id,
    status_id,
    kunde_id
) VALUES (
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    650,
    1,
    1,
    6,
    1,
    1
);

INSERT INTO bestellungen(
    zeitpunkt_bestellt,
    zeitpunkt_vorbereitet,
    zeitpunkt_geliefert,
    zeitpunkt_aufgetrunken,
    initialgewicht,
    platz_id,
    getraenk_id,
    glas_id,
    status_id,
    kunde_id
) VALUES (
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    650,
    2,
    2,
    6,
    1,
    2
);

INSERT INTO bestellungen(
    zeitpunkt_bestellt,
    zeitpunkt_vorbereitet,
    zeitpunkt_geliefert,
    zeitpunkt_aufgetrunken,
    initialgewicht,
    platz_id,
    getraenk_id,
    glas_id,
    status_id,
    kunde_id
) VALUES (
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    650,
    3,
    5,
    3,
    1,
    3
);

INSERT INTO bestellungen(
    zeitpunkt_bestellt,
    zeitpunkt_vorbereitet,
    zeitpunkt_geliefert,
    zeitpunkt_aufgetrunken,
    initialgewicht,
    platz_id,
    getraenk_id,
    glas_id,
    status_id,
    kunde_id
) VALUES (
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    650,
    4,
    3,
    6,
    1,
    4
);

INSERT INTO test_table(name) VALUES ("ABCD");
INSERT INTO test_table(name) VALUES ("CDEF");
INSERT INTO test_table(name) VALUES ("aoeu");
INSERT INTO test_table(name) VALUES ("uetnnthue");

INSERT INTO bestellungen(
    zeitpunkt_bestellt,
    zeitpunkt_vorbereitet,
    zeitpunkt_geliefert,
    zeitpunkt_aufgetrunken,
    initialgewicht,
    platz_id,
    getraenk_id,
    glas_id,
    status_id,
    kunde_id
) VALUES (
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    650,
    7,
    1,
    6,
    2,
    1
);
INSERT INTO bestellungen(
    zeitpunkt_bestellt,
    zeitpunkt_vorbereitet,
    zeitpunkt_geliefert,
    zeitpunkt_aufgetrunken,
    initialgewicht,
    platz_id,
    getraenk_id,
    glas_id,
    status_id,
    kunde_id
) VALUES (
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    650,
    8,
    1,
    6,
    2,
    1
);





INSERT INTO tische(name) VALUES ("TISCH D");

INSERT INTO plaetze(name, tisch_id) VALUES ("PLATZ I",4);
INSERT INTO plaetze(name, tisch_id) VALUES ("PLATZ J",4);
INSERT INTO plaetze(name, tisch_id) VALUES ("PLATZ K",4);

INSERT INTO bestellungen(
    zeitpunkt_bestellt,
    zeitpunkt_vorbereitet,
    zeitpunkt_geliefert,
    zeitpunkt_aufgetrunken,
    initialgewicht,
    platz_id,
    getraenk_id,
    glas_id,
    status_id,
    kunde_id
) VALUES (
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    650,
    9,
    1,
    6,
    2,
    1
);

INSERT INTO bestellungen(
    zeitpunkt_bestellt,
    zeitpunkt_vorbereitet,
    zeitpunkt_geliefert,
    zeitpunkt_aufgetrunken,
    initialgewicht,
    platz_id,
    getraenk_id,
    glas_id,
    status_id,
    kunde_id
) VALUES (
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    650,
    10,
    1,
    6,
    2,
    1
);

INSERT INTO bestellungen(
    zeitpunkt_bestellt,
    zeitpunkt_vorbereitet,
    zeitpunkt_geliefert,
    zeitpunkt_aufgetrunken,
    initialgewicht,
    platz_id,
    getraenk_id,
    glas_id,
    status_id,
    kunde_id
) VALUES (
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    '1980-02-01-12:34:56',
    650,
    11,
    1,
    6,
    1,
    1
);
