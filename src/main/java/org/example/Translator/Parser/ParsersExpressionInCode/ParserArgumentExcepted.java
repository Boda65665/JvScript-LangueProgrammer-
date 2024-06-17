package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.ArgumentExceptedNode;
import org.example.AST.ExpressionNode;
import org.example.Entiy.Code;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Entiy.ValueType;
import org.example.Translator.Parser.ParserBase;

public class ParserArgumentExcepted extends ParserBase {

    public ParserArgumentExcepted(Code code) {
        super(code);
    }

    @Override
    public ExpressionNode parse() {
        ArgumentExceptedNode argumentExceptedNode = new ArgumentExceptedNode(code.getCurrentToken());
        while (!code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(), TokenType.RRAP)){
            Token tokenType = code.requireToken(TokenType.TYPE);
            ValueType exceptedType = ValueType.getTypeFromString(tokenType.text());
            Token tokenName = code.requireToken(TokenType.NAME);
            String nameArg = tokenName.text();
            argumentExceptedNode.addArg(exceptedType,nameArg);
            if (code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(), TokenType.RRAP)) break;
            code.requireToken(TokenType.COMMA);
        }
        return argumentExceptedNode;
    }
}
