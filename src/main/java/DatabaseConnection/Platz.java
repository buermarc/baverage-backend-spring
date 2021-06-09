package DatabaseConnection;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
@Entity

public class Platz implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	
	@ManyToOne 
    private Tisch tisch_id;
	
	@OneToOne (mappedBy="platz_id")
	private Bestellung bestellung;
	
	String name="";
	

}
