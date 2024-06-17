package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.ValueNode;
import org.example.Entiy.Code;
import org.example.Entiy.Token;
import org.example.Entiy.ValueType;
import org.example.Translator.Parser.ParserBase;

public class ParserValue extends ParserBase {
    public ParserValue(Code code) {
        super(code);
    }

    @Override
    public ValueNode parse() {
        Token valueToken = code.requireToken(ValueType.OBJECT.getTokenTypes());
        return new ValueNode(valueToken);
    }
}
