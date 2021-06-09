package DatabaseConnection;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity

public class Glas implements Serializable {
	@Id
	int id;
	
	int leergewicht;
	
	@OneToOne (mappedBy="glas_id")
	private Bestellung bestellung;
}
