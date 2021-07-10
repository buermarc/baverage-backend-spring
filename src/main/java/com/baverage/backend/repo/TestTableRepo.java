package com.baverage.backend.repo;

//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.repository.Repository;

import com.baverage.backend.databaseConnection.TestTable;

/**
 * Erlaubt angepasste Export Mappings des Repositories
 */
//@RepositoryRestResource
@org.springframework.stereotype.Repository
public interface TestTableRepo extends CrudRepository<TestTable, Integer>{
    @Query("SELECT t FROM TestTable t")
    Collection<TestTable> getAllTables();
}
