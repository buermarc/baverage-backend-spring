package com.baverage.backend.service;

import com.baverage.backend.DatabaseConnection.Kunden;

public interface IKundeService {

    Kunden createKunde(String name, int platzId);

}
