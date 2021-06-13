package com.baverage.backend.DatabaseConnection;

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

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bestellungen implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	Date zeitpunkt_bestellt;
	
	Date zeitpunkt_vorbereitet;
	
	Date zeitpunkt_angekommen;
	
	Date zeitpunkt_leergetrunken;
	
	double initialgewicht;
	
	@ManyToOne 
	@JsonBackReference
	Plaetze platz;
	
	// evtl. Many to One
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
	
	@OneToMany (mappedBy="bestellungen")
	@JsonManagedReference
	List <Messpunkte> messpunkte= new ArrayList <Messpunkte> ();

        public Bestellungen(int id, Date zeitpunkt_bestellt, Date zeitpunkt_vorbereitet, Date zeitpunkt_angekommen, Date zeitpunkt_leergetrunken, double initialgewicht) {
            this.id = id;
            this.zeitpunkt_bestellt = zeitpunkt_bestellt;
            this.zeitpunkt_vorbereitet = zeitpunkt_vorbereitet;
            this.zeitpunkt_angekommen = zeitpunkt_angekommen;
            this.zeitpunkt_leergetrunken = zeitpunkt_leergetrunken;
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
