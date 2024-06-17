package org.example.AST;

import org.example.Entiy.Token;

public class LogicNode extends ExpressionNode {
    private final ExpressionNode logicExpression;
    private final StatementsNode ifBody;
    private final StatementsNode elseBody;

    public LogicNode(ExpressionNode logicExpression, StatementsNode ifBody, StatementsNode elseBody,Token branchOperator) {
        super(branchOperator);
        this.logicExpression = logicExpression;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    public ExpressionNode getLogicExpression() {
        return logicExpression;
    }

    public StatementsNode getIfBody() {
        return ifBody;
    }

    public StatementsNode getElseBody() {
        return elseBody;
    }


}
