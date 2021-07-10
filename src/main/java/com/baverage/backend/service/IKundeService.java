package com.baverage.backend.service;

import com.baverage.backend.databaseConnection.Kunden;

/*
 * IKundeService interface, that should be implemented when creating a
 * service class for Kunden.
 */
public interface IKundeService {

    Kunden createKunde(String name, int platzId);

}
