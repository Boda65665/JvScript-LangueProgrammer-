package org.example.Translator.Compiler.CompilerOperations;

import org.example.AST.ExpressionNode;
import org.example.Translator.Compiler.CompilerBase;
import org.example.Entiy.Scope;

public class CompilerCreateVariable extends CompilerBase {
    private final Scope scope;

    public CompilerCreateVariable(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Object compile(ExpressionNode node) {
        scope.setVariable(node);
        return null;
    }
}
