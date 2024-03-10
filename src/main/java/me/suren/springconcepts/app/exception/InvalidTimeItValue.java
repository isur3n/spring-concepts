package me.suren.springconcepts.app.exception;

public class InvalidTimeItValue extends Exception {

    public InvalidTimeItValue(String message) {
        super(message);
    }

    public InvalidTimeItValue(Exception e) {
        super(e);
    }
}
