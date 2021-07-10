package com.baverage.backend.service;

import com.baverage.backend.databaseConnection.Kunden;

public interface IKundeService {

    Kunden createKunde(String name, int platzId);

}
