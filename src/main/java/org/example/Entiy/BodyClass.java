package org.example.Entiy;

import org.example.Translator.BufferFunctions;
import org.example.Translator.Compiler.Scope;

public class BodyClass {
    private final BufferFunctions bufferFunctions = new BufferFunctions();
    private final Scope scope = new Scope(null);

    public BufferFunctions getBufferFunctions() {
        return bufferFunctions;
    }

    public Scope getScope() {
        return scope;
    }
}
