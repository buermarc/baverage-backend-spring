package com.baverage.backend.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
