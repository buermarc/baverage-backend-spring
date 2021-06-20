package com.baverage.backend.service;

import com.baverage.backend.DatabaseConnection.Kunden;
import com.baverage.backend.repo.KundeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class KundeService implements IKundeService {

    @Autowired KundeRepo kundeRepo;

    public Kunden createKunde(String name) {
        Kunden kunde = new Kunden();
        kunde.setName(name);
        kunde.setZeitpunkt_angelegt(new Date());
        kundeRepo.save(kunde);
        return kunde;
    }

}
