package org.example.Translator.Compiler.CompilerOperations;

import org.example.AST.BindOperationNode;
import org.example.AST.ExpressionNode;
import org.example.AST.LogicNode;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Exception.CompilationError;
import org.example.Translator.Compiler.Compiler;
import org.example.Translator.Compiler.CompilerBase;
import org.example.Entiy.Scope;

public class CompilerLogicOperation extends CompilerBase {
    private final Scope scope;
    private final Compiler compiler;

    public CompilerLogicOperation(Scope scope, Compiler compiler) {
        this.scope = scope;
        this.compiler = compiler;
    }

    @Override
    public Object compile(ExpressionNode node) {
        LogicNode logicNode = (LogicNode) node;
        ExpressionNode logicExpression = logicNode.getLogicExpression();
        boolean isTrueLogicExpression = (boolean) compiler.compile(logicExpression);
        scope.setLocalVisible();
        if (isTrueLogicExpression) compiler.compile(logicNode.getIfBody());
        else if (logicNode.getElseBody()!=null)compiler.compile(logicNode.getElseBody());
        scope.setGlobalVisible();
        return null;
    }

    public boolean isTrueLogicExpression(BindOperationNode node) {
        Token compressionOperator = node.getToken();
        Object leftValue = compiler.compile(node.getLeftNode());
        Object rightValue = compiler.compile(node.getRightNode());
        String compressionOperatorString = compressionOperator.text();

        if (compressionOperator.type() == TokenType.LOGIC_OPERATOR){
            if (compressionOperator.text().equals("&")) return (boolean) leftValue && (boolean) rightValue;
            return (boolean) leftValue || (boolean) rightValue;
        }
        if (compressionOperatorString.equals("==") || compressionOperatorString.equals("!=")){
            if (compressionOperatorString.equals("=="))return rightValue.equals(leftValue);
            else return !rightValue.equals(leftValue);
        }
        if (!(leftValue instanceof Number leftOperand) || !(rightValue instanceof Number rightOperand))
            throw new CompilationError("ожидалось число или числавая переменная",node.getPosition());
        return switch (compressionOperator.text()) {
            case "<" -> (leftOperand.doubleValue() < (rightOperand.doubleValue()));
            case ">" -> (leftOperand.doubleValue() > (rightOperand.doubleValue()));
            case "<=" -> (leftOperand.doubleValue() <= (rightOperand.doubleValue()));
            case ">=" -> (leftOperand.doubleValue() >= (rightOperand.doubleValue()));
            default -> false;
        };
    }
}
