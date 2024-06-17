package org.example.Translator.Compiler.CompilerOperations;

import org.example.AST.BindOperationNode;
import org.example.AST.ExpressionNode;
import org.example.Entiy.Position;
import org.example.Entiy.TokenType;
import org.example.Exception.CompilationError;
import org.example.Translator.Compiler.Compiler;
import org.example.Translator.Compiler.CompilerBase;

public class CompilerArithmeticOperation extends CompilerBase {
    private final Compiler compiler;

    public CompilerArithmeticOperation(Compiler compiler) {
        this.compiler = compiler;
    }

    @Override
    public Object compile(ExpressionNode node) {
        TokenType tokenType = node.getToken().type();
        Object firstOperand = compiler.compile(((BindOperationNode) node).getLeftNode());
        Object secondOperand = compiler.compile(((BindOperationNode) node).getRightNode());
        return arithmeticOperations(tokenType,firstOperand,secondOperand,node.getPosition());
    }

    private Object arithmeticOperations(TokenType type, Object firstOperand, Object secondOperand, Position positionInCode)  {
        if (areNumber(firstOperand,secondOperand)) {
            double result = switch (type){
                case PLUS -> sum(firstOperand, secondOperand);
                case MINUS -> subtract(firstOperand, secondOperand);
                case MULTIPLY -> multiply(firstOperand, secondOperand);
                case DIVIDE -> divide(firstOperand, secondOperand,positionInCode);
                default -> throw errorExecuteArithmeticOperations(type,firstOperand,secondOperand,positionInCode);
            };
            if (areInteger(firstOperand,secondOperand))return (int)result;
            return result;
        }
        else if (firstOperand instanceof String || secondOperand instanceof String){
            return switch (type){
                case PLUS -> sumString(firstOperand, secondOperand);
                case MULTIPLY -> multiplyString(firstOperand, secondOperand);
                default -> throw errorExecuteArithmeticOperations(type,firstOperand,secondOperand,positionInCode);
            };
        }
        throw errorExecuteArithmeticOperations(type,firstOperand,secondOperand,positionInCode);
    }

    private double sum(Object firstOperand, Object secondOperand) {
        return Double.parseDouble(String.valueOf(firstOperand)) + Double.parseDouble(String.valueOf(secondOperand));
    }

    private double subtract(Object firstOperand, Object secondOperand) {
        return Double.parseDouble(String.valueOf(firstOperand)) - Double.parseDouble(String.valueOf(secondOperand));
    }

    private double multiply(Object firstOperand, Object secondOperand) {
        return Double.parseDouble(String.valueOf(firstOperand)) * Double.parseDouble(String.valueOf(secondOperand));
    }

    private void validDivideZero(Object secondOperand,Position position) throws CompilationError {
        if (Double.parseDouble(String.valueOf(secondOperand))==0) throw new CompilationError("ошибка: нельзя делить на 0",position);
    }

    private double divide(Object firstOperand, Object secondOperand,Position positionInCode) throws CompilationError {
        validDivideZero(secondOperand,positionInCode);
        return Double.parseDouble(String.valueOf(firstOperand)) / Double.parseDouble(String.valueOf(secondOperand));
    }

    private String sumString(Object firstOperand, Object secondOperand) {
        if (firstOperand instanceof Number) return firstOperand + (String)secondOperand;
        return (String)firstOperand + secondOperand;
    }

    private String multiplyString(Object firstOperand, Object secondOperand) {
        return String.format("%"+secondOperand+"s","").replace(" ",(String)firstOperand);

    }

    private boolean areNumber(Object firstOperand,Object secondOperand){
        return firstOperand instanceof Number && secondOperand instanceof Number;
    }

    private boolean areInteger(Object firstOperand,Object secondOperand) {
        return firstOperand instanceof Integer && secondOperand instanceof Integer;
    }

    private CompilationError errorExecuteArithmeticOperations(TokenType type, Object firstOperand, Object secondOperand, Position positionInCode) {
        return new CompilationError(String.format("ошибка выполнение арифмитической операции %s %s %s", firstOperand, type, secondOperand)
                ,positionInCode);
    }
}
