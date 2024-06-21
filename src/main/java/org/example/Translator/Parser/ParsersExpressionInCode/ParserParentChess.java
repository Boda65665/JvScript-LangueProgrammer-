package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.ExpressionNode;
import org.example.Entiy.Code;
import org.example.Entiy.TokenType;
import org.example.Exception.ParseError;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Parser.ParserBase;

public class ParserParentChess extends ParserBase {
    private final ParserArgumentExcepted parserArgumentExcepted;
    private final BufferFunctions bufferFunctions;

    public ParserParentChess(Code code, BufferFunctions bufferFunctions) {
        super(code);
        parserArgumentExcepted=new ParserArgumentExcepted(code);
        this.bufferFunctions = bufferFunctions;
    }

    @Override
    public ExpressionNode parse() throws ParseError {
        code.requireToken(TokenType.LRAP);
        ExpressionNode node;
        if (code.isCallFunctionOnPos(code.getIndexCurrentToken()-2)) {
            ParserArgument parserArgument = new ParserArgument(code,bufferFunctions);
            node = parserArgument.parse();
        }
        else if (code.isFindLogicExpressionOnPos(code.getIndexCurrentToken()) || code.isFindLogicOperatorOnCurrentPos()){
            node = parseLogicExpression();
        }
        else if (code.isCreateFunctionOnPos(code.getIndexCurrentToken()-2)) {
            node = parserArgumentExcepted.parse();
        }
        else {
            node = parseDataAndFormula();
        }
        code.requireToken(TokenType.RRAP);
        return node;
    }

    private ExpressionNode parseLogicExpression() throws ParseError {
        ParserLogicBranching parserLogicBranching = new ParserLogicBranching(code,bufferFunctions);
        return parserLogicBranching.parseLogicExpression();
    }

    private ExpressionNode parseDataAndFormula() throws ParseError {
        ParserDataAndFormula parserDataAndFormula = new ParserDataAndFormula(code,bufferFunctions);
        return parserDataAndFormula.parse();
    }
}
