package com.baverage.backend.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/*
 *  This is a data transfer object and is responsible for transfering data of empty seats.
 *  
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmptySeat implements Serializable{
    private int bestellung_id;
    private int tisch_id;
    private int platz_id;
    private long plaetze_am_tisch;
    private double fuellstand;
}
