package com.baverage.backend.service;

import com.baverage.backend.databaseConnection.Bestellungen;

/*
 * IBestellungService interface, that should be implemented when creating a
 * service class for Bestellungen.
 */
public interface IBestellungService {

    Bestellungen createBestellung(int platz_id, int getraenk_id, int status_id);

}
