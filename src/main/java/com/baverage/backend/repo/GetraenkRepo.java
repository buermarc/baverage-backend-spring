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
import com.baverage.backend.DatabaseConnection.Bestellungen;
import com.baverage.backend.DatabaseConnection.Getraenke;
import com.baverage.backend.DatabaseConnection.Stati.Status;
import com.baverage.backend.DatabaseConnection.Tische;

/**
 * Erlaubt angepasste Export Mappings des Repositories der Getränke
 */

@org.springframework.stereotype.Repository
public interface GetraenkRepo extends CrudRepository<Getraenke, Integer>{
	
}
