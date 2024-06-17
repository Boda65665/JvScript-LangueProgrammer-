package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.VariableNode;
import org.example.Entiy.Code;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Translator.Parser.ParserBase;

public class ParseVariableCall extends ParserBase {

    public ParseVariableCall(Code code) {
        super(code);
    }

    public VariableNode parse() {
        Token variableToken = code.requireToken(TokenType.NAME);
        return new VariableNode(variableToken);
    }
}