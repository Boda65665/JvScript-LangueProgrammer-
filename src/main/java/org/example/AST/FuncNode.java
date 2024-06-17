package org.example.AST;

import org.example.Entiy.BodyFunc;
import org.example.Entiy.Token;
import org.example.Entiy.ValueType;

public class FuncNode extends ExpressionNode{
    private final BodyFunc bodyFunc;
    private final ArgumentExceptedNode argumentExceptedNode;
    private final ValueType returnType;

    public FuncNode(Token token, ValueType returnType, ArgumentExceptedNode argumentExceptedNode,BodyFunc bodyFunc) {
        super(token);
        this.bodyFunc=bodyFunc;
        this.argumentExceptedNode = argumentExceptedNode;
        this.returnType=returnType;
    }

    public BodyFunc getBodyFunc() {
        return bodyFunc;
    }

    public ArgumentExceptedNode getArgumentExceptedNode() {
        return argumentExceptedNode;
    }

    public ValueType getReturnType() {
        return returnType;
    }

    public String getNameFunc(){
        return token.text();
    }
}
