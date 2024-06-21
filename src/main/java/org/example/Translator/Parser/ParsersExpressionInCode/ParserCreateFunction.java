package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.ArgumentExceptedNode;
import org.example.AST.ExpressionNode;
import org.example.AST.FuncNode;
import org.example.AST.StatementsNode;
import org.example.Entiy.*;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Parser.ParserBase;
import org.example.Translator.Parser.ParserCode;


public class ParserCreateFunction extends ParserBase {
    private final BufferFunctions bufferFunctions;
    private final ParserParentChess parserParentChess;
    private final ParserDataAndFormula parserDataAndFormula;

    public ParserCreateFunction(Code code,BufferFunctions bufferFunctions) {
        super(code);
        this.bufferFunctions = bufferFunctions;
        parserParentChess = new ParserParentChess(code,bufferFunctions);
        parserDataAndFormula=new ParserDataAndFormula(code,bufferFunctions);
    }

    @Override
    public ExpressionNode parse() {
        Token returnTypeToken = code.requireToken(TokenType.TYPE,TokenType.VOID);
        ValueType returnType = ValueType.getTypeFromString(returnTypeToken.text());
        Token nameFuncToken = code.requireToken(TokenType.NAME);
        ArgumentExceptedNode argumentExceptedNode = (ArgumentExceptedNode) parserParentChess.parse();
        bufferFunctions.putNamesArgumentsToBuffer(nameFuncToken.text(),argumentExceptedNode.getAllNameArguments());
        BodyFunc bodyFunc = parseBodyFunc(returnTypeToken.type());
        return new FuncNode(nameFuncToken, returnType, argumentExceptedNode,bodyFunc);
    }

    private BodyFunc parseBodyFunc(TokenType returnTypeToken) {
        ParserCode parserCode = new ParserCode(code,bufferFunctions);
        code.requireToken(TokenType.LCRAP);
        StatementsNode codeNode = (StatementsNode) parserCode.parse(getFlagForSoppingParse(returnTypeToken));
        ExpressionNode returnedData = null;
        if (isIncreasingFunction(returnTypeToken)) {
            code.requireToken(TokenType.RETURN);
            returnedData = parserDataAndFormula.parse();
        }
        code.requireToken(TokenType.RCRAP);
        return new BodyFunc(codeNode,returnedData);
    }

    private boolean isIncreasingFunction(TokenType returnTypeToken) {
        return returnTypeToken==TokenType.TYPE;
    }

    private TokenType getFlagForSoppingParse(TokenType returnTypeToken) {
        return isIncreasingFunction(returnTypeToken) ? TokenType.RETURN:TokenType.RCRAP;
    }
}
