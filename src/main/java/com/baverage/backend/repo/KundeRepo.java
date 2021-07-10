package com.baverage.backend.repo;

//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Date;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.web.servlet.tags.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.repository.Repository;

import com.baverage.backend.dto.OffeneBestellung;
import com.baverage.backend.databaseConnection.Bestellungen;
import com.baverage.backend.databaseConnection.Stati.Status;
import com.baverage.backend.databaseConnection.Kunden;

/**
 * Erlaubt angepasste Export Mappings des Repositories
 */
//@RepositoryRestResource
@org.springframework.stereotype.Repository
public interface KundeRepo extends CrudRepository<Kunden, Integer>{
    @Modifying
    @Transactional
    @Query("UPDATE Kunden k set k.bezahlt = true WHERE k.id = :kunde_id")
    int setKundeBezahlt(@Param("kunde_id") int kunde_id);

    @Query("SELECT k FROM Kunden k WHERE k.platz.id = :platz_id AND k.zeitpunkt_abgeschlossen = null AND k.bezahlt = false")
    Kunden findLatestByPlatzId(@Param("platz_id") int platz_id);
}
