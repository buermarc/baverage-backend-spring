package com.baverage.backend.DatabaseConnection;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Plaetze implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    
    String mac;

    @ManyToOne
    @JsonIgnore
    private Tische tisch;

    @OneToMany(mappedBy = "platz")
    @JsonManagedReference
    private List<Bestellungen> bestellungen;

    String name = "";

    @OneToMany(mappedBy = "platz")
    @JsonManagedReference
    private List<Kunden> kunden;

}
