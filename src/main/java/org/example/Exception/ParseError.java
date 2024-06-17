package org.example.Exception;

import org.example.Entiy.Position;

public class ParseError extends Error {
    public ParseError(String message, Position position){
        super(String.format("на позиции %s %s",position.getPosString(),message));
    }
}
