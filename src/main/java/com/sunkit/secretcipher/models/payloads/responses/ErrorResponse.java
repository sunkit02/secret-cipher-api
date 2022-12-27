package com.sunkit.secretcipher.models.payloads.responses;

public class ErrorResponse {
    private String message;
    private int code;
    private String stackTrace;

    public ErrorResponse(int code, String message, String stackTrace) {
        this.code = code;
        this.message = message;
        this.stackTrace = stackTrace;
    }

    public ErrorResponse(String message, String stackTrace) {
        this.code = 400;
        this.message = message;
        this.stackTrace = stackTrace;
    }

    public ErrorResponse(String message) {
        this.code = 400;
        this.message = message;
        this.stackTrace = "";
    }
}
