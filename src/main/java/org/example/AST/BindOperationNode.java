package org.example.AST;

import org.example.Entiy.Token;

public class BindOperationNode extends ExpressionNode {
    private final ExpressionNode leftNode;
    private final ExpressionNode rightNode;

    public BindOperationNode(Token operator, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(operator);
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }


    public ExpressionNode getLeftNode() {
        return leftNode;
    }

    public ExpressionNode getRightNode() {
        return rightNode;
    }

}
