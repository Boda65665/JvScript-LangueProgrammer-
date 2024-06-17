package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.ExpressionNode;
import org.example.Entiy.BodyClass;
import org.example.Entiy.Code;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Exception.ParseError;
import org.example.Translator.Parser.ParserBase;

public class ParserClass extends ParserBase {

    public ParserClass(Code code) {
        super(code);
    }

    @Override
    public ExpressionNode parse() throws ParseError {
        code.requireToken(TokenType.CLASS);
        Token tokenNameClass = code.requireToken(TokenType.NAME);
        code.requireToken(TokenType.LCRAP);
        BodyClass bodyClass = parseBodyClass();
        code.requireToken(TokenType.RCRAP);
        return null;
    }

    private BodyClass parseBodyClass() {
        return null;
    }
}
