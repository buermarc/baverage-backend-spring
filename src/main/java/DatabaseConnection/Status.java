package DatabaseConnection;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
@Entity

public class Status implements Serializable{
	@Id 
	int id;
	
	String bezeichnung="";
	
	@OneToOne
	Bestellung bestellung;
}
