package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.BindOperationNode;
import org.example.AST.ExpressionNode;
import org.example.AST.LogicNode;
import org.example.AST.StatementsNode;
import org.example.Entiy.Code;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Exception.ParseError;
import org.example.Translator.BufferFunctions;
import org.example.Translator.Parser.ParserBase;
import org.example.Translator.Parser.ParserCode;

public class ParserLogicBranching extends ParserBase {
    private final ParserData parserData;
    private final ParserParentChess parserParentChess;
    private final BufferFunctions bufferFunctions;

    public ParserLogicBranching(Code code, BufferFunctions bufferFunctions) {
        super(code);
        this.bufferFunctions=bufferFunctions;
        parserData=new ParserData(code,bufferFunctions);
        parserParentChess=new ParserParentChess(code,bufferFunctions);
    }

    @Override
    public ExpressionNode parse() {
        Token branchOperator = code.requireToken(TokenType.BRANCH_OPERATOR);
        String branchOperatorString = branchOperator.text();

        if (branchOperatorString.equals("if")) {
            ExpressionNode logicExpression = parserParentChess.parse();
            StatementsNode ifBody = parseBodyLogBranch();
            StatementsNode elseBody = null;
            Token elseBranchToken = code.tokenMatch(TokenType.BRANCH_OPERATOR);
            if (elseBranchToken!=null && elseBranchToken.text().equals("else")) elseBody = parseBodyLogBranch();
            return new LogicNode(logicExpression,ifBody,elseBody,branchOperator);
        }
        throw new ParseError("ожидаелся оператор ветвление IF",code.getPosCurrentToken());
    }

    private StatementsNode parseBodyLogBranch() {
        code.requireToken(TokenType.LCRAP);
        ParserCode parserCode = new ParserCode(code,bufferFunctions);
        StatementsNode bodyLogBranch = (StatementsNode) parserCode.parse(TokenType.RCRAP);
        code.requireToken(TokenType.RCRAP);
        return bodyLogBranch;
    }

    public ExpressionNode parseLogicExpression() {
        ExpressionNode leftNode = parseLogicExpUnitLogicOperator();
        Token operator = code.tokenMatch(TokenType.LOGIC_OPERATOR);
        while (operator != null) {
            leftNode=new BindOperationNode(operator, leftNode, parseLogicExpUnitLogicOperator());
            if (!code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(), TokenType.LOGIC_OPERATOR)) break;
            operator=code.requireToken(TokenType.LOGIC_OPERATOR);
        }
        return leftNode;
    }

    private ExpressionNode parseLogicExpUnitLogicOperator() {
        ExpressionNode leftNode = parserData.parse();
        Token operator = code.tokenMatch(TokenType.COMPRESSION_OPERATOR);
        if (operator==null)return leftNode;
        ExpressionNode rightNode = parserData.parse();

        return new BindOperationNode(operator,leftNode,rightNode);
    }
}
