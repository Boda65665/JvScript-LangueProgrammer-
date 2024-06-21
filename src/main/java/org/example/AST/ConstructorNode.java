package org.example.AST;

import org.example.Entiy.Token;

public class ConstructorNode extends ExpressionNode{

    private final ArgumentExceptedNode argumentExceptedNode;
    private final StatementsNode statementsNode;

    public ConstructorNode(Token token, ArgumentExceptedNode argumentExceptedNode, StatementsNode statementsNode) {
        super(token);
        this.argumentExceptedNode = argumentExceptedNode;
        this.statementsNode = statementsNode;
    }


}
