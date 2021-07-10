package com.baverage.backend.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/*
 * This class contains all information to create a user request.
 */

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest implements Serializable {
    String name;
    int platz_id;
}
