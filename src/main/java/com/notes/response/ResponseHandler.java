package com.notes.response;

public interface ResponseHandler {

    default public void addErrorCodeToResponse(Response response, int status, String code, String message) {
        response.setSuccessful(false);
        response.setStatus(status);
        response.setError(new Error(code, message));
    }
}
