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

INSERT INTO glaeser(name, leergewicht, rfid) VALUES ("ROTWEIN_GLAS", 100, "RFID");
INSERT INTO glaeser(name, leergewicht, rfid) VALUES ("WEISSWEIN_GLAS", 100, "RFID");
INSERT INTO glaeser(name, leergewicht, rfid) VALUES ("WEIZEN_GLAS", 100, "RFID");
INSERT INTO glaeser(name, leergewicht, rfid) VALUES ("PILS_GLAS", 100, "RFID");
INSERT INTO glaeser(name, leergewicht, rfid) VALUES ("SCHORLE_GLAS", 100, "RFID");
INSERT INTO glaeser(name, leergewicht, rfid) VALUES ("500_ML_GLAS", 100, "RFID");

INSERT INTO kunden(name, zeitpunkt_angelegt, platz_id) VALUES ("Alice", "1980-02-01-12:34:56", 1);
INSERT INTO kunden(name, zeitpunkt_angelegt, platz_id) VALUES ("Bob", "1980-02-01-12:34:56", 2);
INSERT INTO kunden(name, zeitpunkt_angelegt, platz_id) VALUES ("Charlie", "1980-02-01-12:34:56", 3);
INSERT INTO kunden(name, zeitpunkt_angelegt, platz_id) VALUES ("Kim", "1980-02-01-12:34:56", 4);

INSERT INTO plaetze(name, tisch_id, mac) VALUES ("PLATZ A", 1, "MAC1");
INSERT INTO plaetze(name, tisch_id, mac) VALUES ("PLATZ B", 1, "MAC2");

INSERT INTO plaetze(name, tisch_id, mac) VALUES ("PLATZ C", 2, "MAC3");
INSERT INTO plaetze(name, tisch_id, mac) VALUES ("PLATZ D", 2, "MAC4");
INSERT INTO plaetze(name, tisch_id, mac) VALUES ("PLATZ E", 2, "MAC5");
INSERT INTO plaetze(name, tisch_id, mac) VALUES ("PLATZ F", 2, "MAC6");

INSERT INTO plaetze(name, tisch_id, mac) VALUES ("PLATZ G", 3, "MAC7");
INSERT INTO plaetze(name, tisch_id, mac) VALUES ("PLATZ H", 3, "MAC8");

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

INSERT INTO plaetze(name, tisch_id, mac) VALUES ("PLATZ I", 4, "MAC9");
INSERT INTO plaetze(name, tisch_id, mac) VALUES ("PLATZ J", 4, "MAC10");
INSERT INTO plaetze(name, tisch_id, mac) VALUES ("PLATZ K", 4, "MAC11");

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

INSERT INTO messpunkte(fuellstand, zeitpunkt, bestellungen_id)
VALUES (0.25, '1980-02-01-12:34:56', 1);
INSERT INTO messpunkte(fuellstand, zeitpunkt, bestellungen_id)
VALUES (0.2, '1980-02-01-12:34:56', 1);
INSERT INTO messpunkte(fuellstand, zeitpunkt, bestellungen_id)
VALUES (0.29, '1980-02-01-12:34:56', 2);
INSERT INTO messpunkte(fuellstand, zeitpunkt, bestellungen_id)
VALUES (0.31, '1980-02-01-12:34:56', 3);
