package com.novoda.tpbot;

public class Response {

    private Optional<Exception> exception;
    private Optional<String> response;

    public static Response from(Exception exception) {
        return new Response(Optional.of(exception), Optional.<String>absent());
    }

    public static Response from(String response) {
        return new Response(Optional.<Exception>absent(), Optional.of(response));
    }

    public Response(Optional<Exception> exception, Optional<String> response) {
        this.exception = exception;
        this.response = response;
    }

    public Optional<Exception> exception() {
        return exception;
    }

    public Optional<String> response() {
        return response;
    }
}
