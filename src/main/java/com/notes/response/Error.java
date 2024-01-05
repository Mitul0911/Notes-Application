package com.notes.response;

import lombok.Data;

@Data
public class Error {
    String code;
    String message;

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
