package com.baverage.backend.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/*
 * This class contains a sucess and errormessage.
 * This is a data transfer object.
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse implements Serializable{
    private boolean success;
    private String errorMessage;
}
