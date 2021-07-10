package com.baverage.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
/*
 * This is a data transfer object which is necessary to read json objects which this information.
 * In this case, this is responsible for showing how many rows are affected.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UpdateQueryResponse extends BasicResponse {
    private int rowsChanged;
}
