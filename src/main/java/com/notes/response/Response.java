package com.notes.response;

import lombok.Data;

@Data
public class Response {
    boolean successful=false;

    int status;

    Object responseObject;

    Error error;
}
