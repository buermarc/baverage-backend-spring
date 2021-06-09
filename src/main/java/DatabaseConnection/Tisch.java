package DatabaseConnection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity

public class Tisch implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@OneToMany (mappedBy="tisch_id")
	List <Platz> plaetze= new ArrayList<Platz>();
	
	String name="";
}
