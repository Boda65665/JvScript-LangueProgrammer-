package org.example.AST;

import org.example.Entiy.Token;

public class UnarOperationNode extends ExpressionNode {
    private final ExpressionNode operand;

    public UnarOperationNode(Token operator, ExpressionNode operand) {
        super(operator);
        this.operand = operand;
    }

    public ExpressionNode getOperand() {
        return operand;
    }
}
