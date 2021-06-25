package com.baverage.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmptySeat {
    private int bestellung_id;
    private int tisch_id;
    private int platz_id;
    private int plaetze_am_tisch;
    private double fuellstand;
}
