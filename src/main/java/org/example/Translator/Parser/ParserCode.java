package org.example.Translator.Parser;

import org.example.AST.*;
import org.example.Entiy.*;
import org.example.Entiy.BufferFunctions;
import org.example.Exception.ParseError;
import org.example.Translator.Parser.ParsersExpressionInCode.*;

import java.text.ParseException;

public class ParserCode extends ParserBase{

    private final ParserVariableCreate parserVariableCreate;
    private final ParserCallFunction parseCallFunction;
    private final ParserLogicBranching parserLogicBranching;
    private final ParserCreateFunction parserCreateFunction;
    private final ParserVariableChange parserVariableChange;

    public ParserCode(Code code,BufferFunctions bufferFunctions) {
        super(code);
        parserVariableCreate = new ParserVariableCreate(code,bufferFunctions);
        parseCallFunction = new ParserCallFunction(code,bufferFunctions);
        parserCreateFunction = new ParserCreateFunction(code,bufferFunctions);
        parserLogicBranching = new ParserLogicBranching(code,bufferFunctions);
        parserVariableChange = new ParserVariableChange(code,bufferFunctions);
    }

    @Override
    public ExpressionNode parse() {
        return parse(null);
    }

    public ExpressionNode parse(TokenType flagForStopParsing) {
        StatementsNode root = new StatementsNode();
        while (code.isRemainedToken() && !isActiveFlagForStopParsing(flagForStopParsing)) {
            ExpressionNode codeNode = parseExpression();
            code.requireToken(TokenType.END);
            root.addNode(codeNode);
        }
        return root;
    }

    private boolean isActiveFlagForStopParsing(TokenType flagForStopParsing) {
        return flagForStopParsing!=null && code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(),flagForStopParsing);
    }

    private ExpressionNode parseExpression() {
        if (code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(),TokenType.VOID)) return parserCreateFunction.parse();
        if (code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(),TokenType.TYPE)){
            if (code.isCreateFunctionOnPos(code.getIndexCurrentToken()+1)) return parserCreateFunction.parse();
            return parserVariableCreate.parse();
        }
        if (code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(),TokenType.BRANCH_OPERATOR)) return parserLogicBranching.parse();
        if (code.isCallFunctionOnPos(code.getIndexCurrentToken())) return parseCallFunction.parse();
        if (code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(),TokenType.NAME)) return  parserVariableChange.parse();
        throw new ParseError("был вызван не существующий тип операции",code.getPosCurrentToken());
    }
}

