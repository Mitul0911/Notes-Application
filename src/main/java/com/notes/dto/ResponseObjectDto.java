package com.notes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseObjectDto {
    UserDto userDetails;
    String token;
    List<NotesDto> notes;
}
