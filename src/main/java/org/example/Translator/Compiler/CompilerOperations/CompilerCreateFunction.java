package org.example.Translator.Compiler.CompilerOperations;

import org.example.AST.ExpressionNode;
import org.example.AST.FuncNode;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Compiler.CompilerBase;

public class CompilerCreateFunction extends CompilerBase {
    private final BufferFunctions bufferFunctions;

    public CompilerCreateFunction(BufferFunctions bufferFunctions) {
        this.bufferFunctions = bufferFunctions;
    }

    @Override
    public Object compile(ExpressionNode node) {
        FuncNode funcNode = (FuncNode) node;
        String nameFunc = funcNode.getNameFunc();
        bufferFunctions.putFunction(nameFunc,funcNode);
        return null;
    }
}
