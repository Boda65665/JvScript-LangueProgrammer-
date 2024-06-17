package org.example.Exception;

public class ParseFileError extends Error {
    public ParseFileError(String message){
        super(message);
    }
}
