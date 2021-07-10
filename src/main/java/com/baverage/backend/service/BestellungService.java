package com.baverage.backend.service;

import com.baverage.backend.databaseConnection.Bestellungen;
import com.baverage.backend.repo.BestellungRepo;
import com.baverage.backend.repo.GetraenkRepo;
import com.baverage.backend.repo.KundeRepo;
import com.baverage.backend.repo.PlatzRepo;
import com.baverage.backend.repo.StatusRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BestellungService implements IBestellungService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BestellungService.class);

    @Autowired PlatzRepo platzRepo;
    @Autowired GetraenkRepo getraenkRepo;
    @Autowired StatusRepo statusRepo;
    @Autowired KundeRepo kundeRepo;
    @Autowired BestellungRepo bestellungRepo;

    public Bestellungen createBestellung(int platz_id, int getraenk_id, int status_id) throws NoSuchElementException {
        LOGGER.info("Create a new bestellung with p: {}, g: {}, s: {}", platz_id, getraenk_id, status_id);
        Bestellungen bestellung = new Bestellungen();
        bestellung.setPlatz(platzRepo.findById(platz_id).get());
        bestellung.setGetraenk(getraenkRepo.findById(getraenk_id).get());
        bestellung.setStatus(statusRepo.findById(status_id).get());
        bestellung.setKunde(kundeRepo.findLatestByPlatzId(platz_id));
        bestellung.setZeitpunkt_bestellt(new Date());
        bestellungRepo.save(bestellung);
        return bestellung;
    }

}
