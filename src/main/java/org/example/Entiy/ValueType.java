package org.example.Entiy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ValueType {
    INT(TokenType.INTEGER),
    DOUBLE(TokenType.DOUBLE),
    BOOL(TokenType.BOOL),
    TYPE(TokenType.TYPE),
    STRING(TokenType.STRING),
    NUMBER(INT,DOUBLE),
    OBJECT(TYPE,NUMBER,STRING,BOOL);

    ValueType(TokenType... tokenTypes) {
        this.tokenTypes = tokenTypes;
    }
    ValueType(ValueType... valueTypes){
        List<TokenType> tokenTypeList = new ArrayList<>();
        for (ValueType type : valueTypes) {
            tokenTypeList.addAll(Arrays.asList(type.tokenTypes));
        }
        this.tokenTypes=tokenTypeList.toArray(TokenType[]::new);
    }

    private final TokenType[] tokenTypes;
    public TokenType[] getTokenTypes(){
        return tokenTypes;
    }

    public static ValueType getTypeFromString(String type) {
        return switch (type) {
            case "int" -> INT;
            case "double" -> DOUBLE;
            case "string" -> STRING;
            case "bool" -> BOOL;
            case "object" -> OBJECT;
            case "TYPE" -> TYPE;
            default -> null;
        };
    }

    private static ValueType getTypeFromTokenType(TokenType tokenType) {
        return switch (tokenType){
            case DOUBLE -> DOUBLE;
            case BOOL -> BOOL;
            case STRING -> STRING;
            case INTEGER -> INT;
            case TYPE -> TYPE;
            default -> null;
        };
    }

    public static boolean instance(TokenType tokenTypeArg, ValueType exceptedTypeArg) {
        ValueType argType = getTypeFromTokenType(tokenTypeArg);
        return argType==exceptedTypeArg;
    }


}
