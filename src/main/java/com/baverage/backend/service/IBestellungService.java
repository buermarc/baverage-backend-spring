package com.baverage.backend.service;

import com.baverage.backend.databaseConnection.Bestellungen;

public interface IBestellungService {

    Bestellungen createBestellung(int platz_id, int getraenk_id, int status_id);

}
