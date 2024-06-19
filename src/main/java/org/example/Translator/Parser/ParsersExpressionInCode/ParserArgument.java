package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.ArgumentNode;
import org.example.AST.ExpressionNode;
import org.example.Entiy.Code;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Exception.ParseError;
import org.example.Translator.BufferFunctions;
import org.example.Translator.Parser.ParserBase;

public class ParserArgument extends ParserBase {
    private final ParserFormula parserFormula;
    private final ParserDataAndFormula parserDataAndFormula;
    private final BufferFunctions bufferFunctions;

    public ParserArgument(Code code, BufferFunctions bufferFunctions) {
        super(code);
        parserFormula = new ParserFormula(code,bufferFunctions);
        parserDataAndFormula = new ParserDataAndFormula(code,bufferFunctions);
        this.bufferFunctions = bufferFunctions;
    }

    @Override
    public ArgumentNode parse() {
        String nameCalledFunc = getNameCalledFunc();
        String[] namesArguments = getNamesArgumentsFunction(nameCalledFunc);
        if (namesArguments==null) {
            throw new ParseError(String.format("была вызвана не существующая функция %s",nameCalledFunc), code.getPosCurrentToken());
        }
        ArgumentNode argumentNode = new ArgumentNode(code.getCurrentToken());
        for (String nameArgument : namesArguments) {
            argumentNode.putArg(nameArgument,parseArgumentValue());
            if (code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(), TokenType.RRAP)) break;
            code.requireToken(TokenType.COMMA);
        }
        return argumentNode;
    }

    private String getNameCalledFunc() {
        Token tokenCallingFunction = code.getTokenOnPos(code.getIndexCurrentToken()-2);
        return tokenCallingFunction.text();
    }

    public String[] getNamesArgumentsFunction(String nameCalledFunc) {
        return bufferFunctions.getNamesArgumentFunction(nameCalledFunc);
    }

    private ExpressionNode parseArgumentValue() {
        if (code.isFindLogicExpressionOnPos(code.getIndexCurrentToken())){
            return parseLogicExpression();
        }
        ExpressionNode value = parserDataAndFormula.parse();
        if (code.isFindArithmeticOperatorOnPos(code.getIndexCurrentToken())){
            value = parserFormula.parse(value);
        }
        return value;
    }

    private ExpressionNode parseLogicExpression() {
        ParserLogicBranching parserLogicBranching = new ParserLogicBranching(code,bufferFunctions);
        return parserLogicBranching.parse();
    }
}
