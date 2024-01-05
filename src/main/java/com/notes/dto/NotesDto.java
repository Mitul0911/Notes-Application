package com.notes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NotesDto {
    String userName;
    long id;
    String description;
    String query;
}
