package org.example.exceptions;

public class PlayerNameIsTooLongException extends IllegalArgumentException{
    public PlayerNameIsTooLongException(){}
    public PlayerNameIsTooLongException(String message) {
        super(message);
    }
}
