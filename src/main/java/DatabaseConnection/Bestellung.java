package DatabaseConnection;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
@Entity

public class Bestellung implements Serializable{

	@Id
	int id;
	
	Date zeitpunkt_bestellt;
	
	Date zeitpunkt_vorbereitet;
	
	Date zeitpunkt_angekommen;
	
	Date zeitpunkt_leergetrunken;
	
	double initialgewicht;
	
	@OneToOne 
	Platz platz_id;
	
	// evtl. Many to One
	@OneToOne
	Getrank getrank_id;
	
	@OneToOne
	Glas glas_id;
	
	@OneToOne
	Status status_id;
	
	@ManyToOne
	Kunde kunde_id;
	
	@OneToMany (mappedBy="bestellung")
	List <Messpunkt> messpunkte= new ArrayList <Messpunkt> ();
	
}
