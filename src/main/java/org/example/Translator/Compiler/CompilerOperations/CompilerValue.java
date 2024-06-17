package org.example.Translator.Compiler.CompilerOperations;

import org.example.AST.ExpressionNode;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Exception.CompilationError;
import org.example.Translator.Compiler.CompilerBase;

public class CompilerValue extends CompilerBase {
    @Override
    public Object compile(ExpressionNode node) {
        return getValueFromValueNode(node);
    }

    private Object getValueFromValueNode(ExpressionNode node) {
        Token tokenValue = node.getToken();
        String value = tokenValue.text();
        TokenType typeValue = tokenValue.type();
        return switch (typeValue){
            case INTEGER -> Integer.parseInt(value);
            case DOUBLE -> Double.parseDouble(value);
            case STRING -> removeQuotes(value);
            case BOOL -> Boolean.parseBoolean(value);
            default -> throw new CompilationError("ожидалась пременная",node.getPosition());
        };
    }
    private Object removeQuotes(String value) {return value.substring(1,value.length()-1);}
}
