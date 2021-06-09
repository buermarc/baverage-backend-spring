package DatabaseConnection;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
@Entity

public class Getrank implements Serializable{

	@Id
	int id;
	
	@OneToOne (mappedBy="getrank_id")
	private Bestellung bestellung;
	
	String name="";
	
	int groeße;
	
	double preis;
	
	double alkoholgehalt;
}
