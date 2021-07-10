package com.baverage.backend.databaseConnection;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import com.fasterxml.jackson.annotation.JsonBackReference;
/*
 * This class contains all the necessary information about the measuring points.
 * To use Getters and Setters, you have to have Lombok installed. 
 * This class is a database table.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Messpunkte implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	double fuellstand;
	
	Date zeitpunkt;
	
	@ManyToOne
	@JsonBackReference
	private Bestellungen bestellungen;

}
