package com.notes.dto;

import lombok.Data;

@Data
public class RequestDto {
    long id;
    String description;
    String query;
    String firstName;
    String lastName;
    String userName;
    String sharedUserName;
    String password;
    String confirmPassword;
}
