package com.baverage.backend.dto;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/*
 * This is a data transfer object which is necessary to read json objects which this information.
 * In this case, this is responsible for the delivery process.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lieferung implements Serializable{
    int id;
    int tisch_id;
    Date zeitpunkt_vorbereitet;
}

