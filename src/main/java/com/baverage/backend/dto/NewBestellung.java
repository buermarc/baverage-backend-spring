package com.baverage.backend.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/*
 * This is a data transfer object which is necessary to read json objects which this information.
 * In this case, this is responsible for the new orders.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewBestellung implements Serializable {
    int platz_id;
    int getraenk_id;
}
