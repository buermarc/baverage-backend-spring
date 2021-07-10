package com.baverage.backend.repo;

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

import com.baverage.backend.dto.EmptySeat;
import com.baverage.backend.dto.OffeneBestellung;
import com.baverage.backend.dto.Lieferung;
import com.baverage.backend.databaseConnection.Bestellungen;

/**
 * Erlaubt angepasste Export Mappings des Repositories für die Bestellungen
 */

@org.springframework.stereotype.Repository
public interface BestellungRepo extends CrudRepository<Bestellungen, Integer> {
	
	// Get all orders with status 1 or 2
	@Query("SELECT NEW com.baverage.backend.dto.OffeneBestellung(b.id, platz.tisch.id, get.name, get.groesse, b.status.id) FROM Bestellungen b INNER JOIN b.getraenk get ON b.getraenk = get.id INNER JOIN b.platz platz ON b.platz = platz.id WHERE b.status = 1 OR b.status = 2")
	Collection<OffeneBestellung> getOffeneBestellungen();

	// Update of an order containing status and time
	@Modifying
	@Transactional
	@Query("UPDATE Bestellungen b set b.status.id = :status_id, b.zeitpunkt_vorbereitet = :dateNow WHERE b.id = :bestellungs_id")
	int setBestellungsStatusVorbereitet(@Param("bestellungs_id") int bestellungs_id, @Param("dateNow") Date dateNow,
			@Param("status_id") int status_id);

	// Get the status of all orders whuch spezific id
	@Query("SELECT b.status.id FROM Bestellungen b WHERE b.id =:id ")
	int getStatusForBestellung(@Param("id") int id);

	// Get all deliveries with status id
	@Query("SELECT NEW com.baverage.backend.dto.Lieferung(b.id, p.tisch.id, b.zeitpunkt_vorbereitet) FROM Bestellungen b INNER JOIN b.platz p on b.platz.id = p.id WHERE NOT EXISTS (SELECT ba FROM Bestellungen ba INNER JOIN ba.platz pa ON ba.platz.id = pa.id WHERE ba.status.id = 1 AND pa.tisch.id = p.tisch.id) AND b.status.id = :status_id")
	Collection<Lieferung> getLieferungen(@Param("status_id") int status_id);

	// get all empty seats
	@Query("select new com.baverage.backend.dto.EmptySeat( b.id, p.tisch.id, b.platz.id, ( select count(*) from b.platz pl where pl.tisch.id = p.tisch.id group by p.tisch.id) as tische_anzahl, m.fuellstand) from Bestellungen b inner join b.platz p on b.platz.id = p.id inner join b.messpunkte m on b.id = m.bestellungen.id where m.fuellstand < 0.3 and m.id = ( select max(me.id) from b.messpunkte me where me.bestellungen.id = b.id)")
	Collection<EmptySeat> getLeerePlaetze();

	// Get all orders with mac adress and status ids
	@Query("select b from Bestellungen b where b.platz.mac = :mac and b.status.id in (:status_two, :status_three)")
	Collection<Bestellungen> getOrderByMacWhereStatusIn(@Param("mac") String mac, @Param("status_two") int status_two,
			@Param("status_three") int status_three);

	
	// get all orders with Bestellung with status 3
	@Query("select b from Bestellungen b where b.glas.rfid = :rfid and b.status.id in (:status_two, :status_three)")
	Collection<Bestellungen> getOrderByRfidWhereStatusIn(@Param("rfid") String rfid,
			@Param("status_two") int status_two, @Param("status_three") int status_three);
}
