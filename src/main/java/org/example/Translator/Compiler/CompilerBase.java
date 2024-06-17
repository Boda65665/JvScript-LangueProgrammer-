package org.example.Translator.Compiler;

import org.example.AST.ExpressionNode;

public abstract class CompilerBase {
    public abstract Object compile(ExpressionNode node);
}
