package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.BindOperationNode;
import org.example.AST.ExpressionNode;
import org.example.Entiy.Code;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Translator.BufferFunctions;
import org.example.Translator.Parser.ParserBase;

public class ParserFormula extends ParserBase {
    ParserData parserData;
    public ParserFormula(Code code, BufferFunctions bufferFunctions) {
        super(code);
        parserData=new ParserData(code,bufferFunctions);
    }

    @Override
    public ExpressionNode parse() {
        return parse(null);
    }

    public ExpressionNode parse(ExpressionNode leftNode) {
        leftNode = (leftNode==null)?parserData.parse():leftNode;
        Token operator = code.tokenMatch(TokenType.MINUS, TokenType.PLUS, TokenType.MULTIPLY, TokenType.DIVIDE);
        while (operator != null) {
            boolean isMultiplicationOrDivision = operator.type() == TokenType.MULTIPLY || operator.type() == TokenType.DIVIDE;
            boolean isAdditionOrSubtraction = (operator.type() == TokenType.MINUS || operator.type() == TokenType.PLUS);
            boolean nextTokenIsNotMultiplicationOrDivision = !code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken() + 1, TokenType.MULTIPLY, TokenType.DIVIDE);
            if (isMultiplicationOrDivision || (isAdditionOrSubtraction && nextTokenIsNotMultiplicationOrDivision)) {
                ExpressionNode rightNode = parserData.parse();
                leftNode = new BindOperationNode(operator, leftNode, rightNode);
                operator = code.tokenMatch(TokenType.MINUS, TokenType.PLUS, TokenType.MULTIPLY, TokenType.DIVIDE);

            } else if (code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken() + 1, TokenType.MULTIPLY, TokenType.DIVIDE)) {
                    ExpressionNode rightNode = parserData.parse();
                    Token operatorPrivileged = code.tokenMatch(TokenType.MULTIPLY, TokenType.DIVIDE);
                    rightNode = new BindOperationNode(operatorPrivileged, rightNode,parsePrivilegedFormula());
                    leftNode = new BindOperationNode(operator, leftNode, rightNode);
                    operator = code.tokenMatch(TokenType.MINUS, TokenType.PLUS, TokenType.MULTIPLY, TokenType.DIVIDE);
            }
        }
        return leftNode;
    }

    private ExpressionNode parsePrivilegedFormula() {
        ExpressionNode leftNode = parserData.parse();
        Token operator=code.tokenMatch(TokenType.DIVIDE,TokenType.MULTIPLY);

        while (operator!=null && code.getIndexCurrentToken() < code.getCountToken()-1){
            ExpressionNode rightNode = parserData.parse();
            leftNode = new BindOperationNode(operator,leftNode,rightNode);
            operator=code.tokenMatch(TokenType.DIVIDE,TokenType.MULTIPLY);
        }
        return leftNode;
    }
}
