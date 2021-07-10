package com.baverage.backend.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/*
 * This is a data transfer object which is necessary to read json objects which this information.
 * In this case, this is responsible for orders which are not delivered yet.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OffeneBestellung implements Serializable{
    int id;
    int tisch_id;
    String getraenkname;
    int getraengroesse;
    int status;
}
