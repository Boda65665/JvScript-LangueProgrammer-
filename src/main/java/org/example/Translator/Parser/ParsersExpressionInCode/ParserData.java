package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.ExpressionNode;
import org.example.Entiy.Code;
import org.example.Entiy.TokenType;
import org.example.Entiy.ValueType;
import org.example.Exception.ParseError;
import org.example.Translator.BufferFunctions;
import org.example.Translator.Parser.ParserBase;

public class ParserData extends ParserBase {
    private final ParserValue parserValue;
    private final ParserParentChess parserParentChess;
    private final ParserCallFunction parseCallFunction;
    private final ParseVariableCall parseVariableCall;

    public ParserData(Code code, BufferFunctions bufferFunctions) {
        super(code);
        parserValue = new ParserValue(code);
        parserParentChess = new ParserParentChess(code,bufferFunctions);
        parseCallFunction = new ParserCallFunction(code,bufferFunctions);
        parseVariableCall = new ParseVariableCall(code);
    }

    @Override
    public ExpressionNode parse() {
        if (code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(), TokenType.LRAP)) {
            return parserParentChess.parse();
        }
        if (code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(), ValueType.OBJECT.getTokenTypes())) {
            return parserValue.parse();
        }
        if (code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(),TokenType.NAME)) {
            return (code.isCallFunctionOnPos(code.getIndexCurrentToken())) ? parseCallFunction.parse() : parseVariableCall.parse();
        }
        throw new ParseError("ожидается variable or value or functionCall", code.getPosCurrentToken());
    }
}
