package com.baverage.backend.repo;

import org.springframework.data.repository.CrudRepository;

import com.baverage.backend.DatabaseConnection.Plaetze;

/**
 * Erlaubt angepasste Export Mappings des Repositories
 */
@org.springframework.stereotype.Repository
public interface PlatzRepo extends CrudRepository<Plaetze, Integer>{
	
}
