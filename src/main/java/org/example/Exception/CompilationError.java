package org.example.Exception;

import org.example.Entiy.Position;

public class CompilationError extends Error{
    public CompilationError(String message, Position position){
        super(String.format("на позиции %s %s",position.getPosString(),message));
    }

}
