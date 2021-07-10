package com.baverage.backend.databaseConnection;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
/*
 * This class contains all the necessary information about the drinking classes.
 * To use Getters and Setters, you have to have Lombok installed. 
 * This class is a database table.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Glaeser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    String rfid;

    int leergewicht;

    @OneToMany(mappedBy = "glas")
    @JsonBackReference
    private List<Bestellungen> bestellungen;
}
