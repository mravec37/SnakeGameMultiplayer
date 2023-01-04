package org.example.exceptions;

public class PlayerNameIsEmptyException extends IllegalArgumentException{
    public PlayerNameIsEmptyException(){
    }
    public PlayerNameIsEmptyException(String message) {
        super(message);
    }
}
