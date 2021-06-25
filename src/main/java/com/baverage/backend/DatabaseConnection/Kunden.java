package com.baverage.backend.DatabaseConnection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.sun.istack.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Kunden implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToMany(mappedBy = "kunde")
    @JsonBackReference
    private List<Bestellungen> bestellungen = new ArrayList<Bestellungen>();

    Date zeitpunkt_angelegt;

    Date zeitpunkt_abgeschlossen;

    @ManyToOne
    @JsonBackReference
    private Plaetze platz;

    @NotNull
    private String name = "";

    @Column(columnDefinition = "boolean default false")
    private boolean bezahlt;
}
