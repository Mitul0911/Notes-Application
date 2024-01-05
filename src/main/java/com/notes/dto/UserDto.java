package com.notes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {
    String firstName;
    String lastName;
    String userName;
    String password;
    String confirmPassword;
}
