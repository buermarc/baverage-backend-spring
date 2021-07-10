package com.baverage.backend.databaseConnection;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
/*
 * This class contrains all the necessary information about drinks such as name, size etc..
 * To use Getters and Setters, you have to have Lombok installed. 
 * This class is a database table.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Getraenke implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@OneToMany (mappedBy="getraenk")
        @JsonBackReference
	private List<Bestellungen> bestellungen;
	
	String name="";
	
	int groesse;
	
	double preis;
	
	double alkoholgehalt;
}
