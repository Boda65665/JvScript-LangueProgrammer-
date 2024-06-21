package org.example.Translator.Compiler.CompilerOperations;

import org.example.AST.ExpressionNode;
import org.example.Entiy.Token;
import org.example.Translator.Compiler.CompilerBase;
import org.example.Entiy.Scope;

public class CompilerCallVariable extends CompilerBase {
    private final Scope scope;

    public CompilerCallVariable(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Object compile(ExpressionNode node) {
        Token nameVarToken = node.getToken();
        String nameVar = nameVarToken.text();
        return scope.getValueFromVariable(nameVar,node.getPosition());
    }
}
