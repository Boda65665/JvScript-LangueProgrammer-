package org.example.Translator.Parser;

import org.example.AST.ExpressionNode;
import org.example.Entiy.Code;
import org.example.Exception.ParseError;

public abstract class ParserBase {
    protected final Code code;

    public ParserBase(Code code) {
        this.code = code;
    }

    public abstract ExpressionNode parse() throws ParseError;
}
