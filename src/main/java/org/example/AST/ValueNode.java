package org.example.AST;

import org.example.Entiy.Token;

public class ValueNode extends ExpressionNode {
    public ValueNode(Token value) {
        super(value);
    }
}
