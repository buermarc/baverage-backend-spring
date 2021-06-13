package com.baverage.backend.repo;

//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.repository.Repository;

import com.baverage.backend.DatabaseConnection.AktiveBestellung;
import com.baverage.backend.DatabaseConnection.Bestellungen;

/**
 * Erlaubt angepasste Export Mappings des Repositories
 */
//@RepositoryRestResource
@org.springframework.stereotype.Repository
public interface BestellungRepo extends CrudRepository<Bestellungen, Integer>{
    @Query("SELECT NEW com.baverage.backend.DatabaseConnection.AktiveBestellung(b.id, platz.tisch.id, get.name, get.groesse) FROM Bestellungen b INNER JOIN b.getraenk get ON b.getraenk = get.id INNER JOIN b.platz platz ON b.platz = platz.id WHERE b.status = 1 ") 
    Collection<AktiveBestellung> getAktiveBestellungen();

    @Query("SELECT b FROM Bestellungen b")
    Collection<Bestellungen> getAlleBestellungen();

    @Query("SELECT b FROM Bestellungen b WHERE b.id = 1")
    Collection<Bestellungen> getID();
}
