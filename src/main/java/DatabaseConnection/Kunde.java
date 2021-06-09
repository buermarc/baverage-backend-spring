package DatabaseConnection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;


import com.sun.istack.NotNull;

@Data
@Entity


public class Kunde implements Serializable {
	
	@Id
	int id;
	
	@OneToMany(mappedBy="kunde_id")
	private List <Bestellung> bestellungen= new ArrayList <Bestellung>();
	
    @NotNull
    private String name = "";
}
