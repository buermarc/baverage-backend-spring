package com.baverage.backend.service;

import com.baverage.backend.databaseConnection.Kunden;
import com.baverage.backend.repo.KundeRepo;
import com.baverage.backend.repo.PlatzRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class KundeService implements IKundeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KundeService.class);

    @Autowired KundeRepo kundeRepo;
    @Autowired PlatzRepo platzRepo;

    public Kunden createKunde(String name, int platzId) throws NoSuchElementException {
        LOGGER.error("Create new Kunde with n: {}, p: {}", name, platzId);
        Kunden kunde = new Kunden();
        kunde.setName(name);
        kunde.setZeitpunkt_angelegt(new Date());
        kunde.setPlatz(platzRepo.findById(platzId).get());
        kundeRepo.save(kunde);
        return kunde;
    }

}
