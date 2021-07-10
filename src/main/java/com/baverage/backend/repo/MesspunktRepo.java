package com.baverage.backend.repo;

import org.springframework.data.repository.CrudRepository;

import com.baverage.backend.DatabaseConnection.Messpunkte;

/**
 * Erlaubt angepasste Export Mappings des Repositories der Messpunkte
 */
@org.springframework.stereotype.Repository
public interface MesspunktRepo extends CrudRepository<Messpunkte, Integer>{
	
}
