package com.baverage.backend.DatabaseConnection;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Stati implements Serializable{

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	String bezeichnung="";
	
	@OneToMany (mappedBy = "status")
        @JsonBackReference
	List<Bestellungen> bestellungen;
}
