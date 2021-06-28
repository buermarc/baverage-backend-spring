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
public class NewBestellung implements Serializable {
    int platz_id;
    int getraenk_id;
}
