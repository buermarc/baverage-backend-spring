package DatabaseConnection;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity

public class Messpunkt implements Serializable{
	
	@Id
	int id;
	
	double fuellstand;
	
	Date zeitpunkt;
	
	@ManyToOne 
	private Bestellung bestellung;

}
