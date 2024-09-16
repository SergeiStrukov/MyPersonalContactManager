package com.example.MyPersonalContactManager.models.RequestResponseModels;

import org.springframework.stereotype.Component;

@Component
public class ResponseAPI {
    private Object response;
    private Error error;

    public ResponseAPI() {
    }

    public ResponseAPI(Object response) {
        this.response = response;
    }

    public ResponseAPI(Error error) {
        this.error = error;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public void setSuccess(Object response) {
        this.response = response;
        this.error = null; // Clear error if response is set
    }

    public void setError(int statusCode, String message) {
        this.error = new Error();
        this.response = null; // Clear response if
    }
}