package com.baverage.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/*
 * This is a data transfer obejct which is necessary to read json objects which this information.
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdClass implements Serializable{
    private int id;
}
