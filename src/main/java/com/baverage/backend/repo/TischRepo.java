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

import com.baverage.backend.dto.OffeneBestellung;
import com.baverage.backend.databaseConnection.Bestellungen;
import com.baverage.backend.databaseConnection.Stati.Status;
import com.baverage.backend.databaseConnection.Tische;

/**
 * Erlaubt angepasste Export Mappings des Repositories des Tisches
 */

@org.springframework.stereotype.Repository
public interface TischRepo extends CrudRepository<Tische, Integer>{
	
}
