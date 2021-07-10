package com.baverage.backend.repo;

import org.springframework.data.repository.CrudRepository;

import com.baverage.backend.databaseConnection.Stati;

/**
 * Erlaubt angepasste Export Mappings des Repositories
 */
@org.springframework.stereotype.Repository
public interface StatusRepo extends CrudRepository<Stati, Integer>{
	
}
