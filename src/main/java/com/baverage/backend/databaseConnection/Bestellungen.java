package com.baverage.backend.databaseConnection;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/*
 * This class contains all attributes for the database design of the orders ("Bestellungen").
 * There are references to seats, drinks, status and customers.
 * This class is a database table.
 */
@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bestellungen implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	Date zeitpunkt_bestellt;

	Date zeitpunkt_vorbereitet;

	Date zeitpunkt_geliefert;

	Date zeitpunkt_aufgetrunken;

	double initialgewicht;

	@ManyToOne
	@JsonBackReference
	Plaetze platz;

	@ManyToOne
	@JsonBackReference
	Getraenke getraenk;

	@ManyToOne
	@JsonBackReference
	Glaeser glas;

	@ManyToOne
	@JsonBackReference
	Stati status;

	@ManyToOne
	@JsonBackReference
	Kunden kunde;

	@OneToMany(mappedBy = "bestellungen")
	@JsonManagedReference
	List<Messpunkte> messpunkte = new ArrayList<Messpunkte>();

	public Bestellungen(int id, Date zeitpunkt_bestellt, Date zeitpunkt_vorbereitet, Date zeitpunkt_geliefert,
			Date zeitpunkt_aufgetrunken, double initialgewicht) {
		this.id = id;
		this.zeitpunkt_bestellt = zeitpunkt_bestellt;
		this.zeitpunkt_vorbereitet = zeitpunkt_vorbereitet;
		this.zeitpunkt_geliefert = zeitpunkt_geliefert;
		this.zeitpunkt_aufgetrunken = zeitpunkt_aufgetrunken;
		this.initialgewicht = initialgewicht;
	}

	public Bestellungen(int id) {
		this.id = id;
	}

	public Bestellungen(int id, long initialgewicht) {
		this.id = id;
		this.initialgewicht = initialgewicht;
	}

	public Bestellungen(int id, Getraenke getraenk) {
		this.id = id;
		this.getraenk = getraenk;
	}
}
