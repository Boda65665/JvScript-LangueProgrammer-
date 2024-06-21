package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.BindOperationNode;
import org.example.AST.ExpressionNode;
import org.example.AST.VariableNode;
import org.example.Entiy.Code;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Entiy.ValueType;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Parser.ParserBase;

public class ParserVariableCreate extends ParserBase {
    private final BufferFunctions bufferFunctions;

    public ParserVariableCreate(Code code, BufferFunctions bufferFunctions) {
        super(code);
        this.bufferFunctions = bufferFunctions;
    }

    @Override
    public ExpressionNode parse() {
        ParserFormula parserFormula = new ParserFormula(code,bufferFunctions);
        Token typeVariableToken = code.requireToken(TokenType.TYPE);
        VariableNode variableNode = parseVariableNode();
        Token assignOperator = code.requireToken(TokenType.ASSIGN);
        ValueType typeVar = ValueType.getTypeFromString(typeVariableToken.text());
        variableNode.setType(typeVar);
        ExpressionNode value = parserFormula.parse(null);
        return new BindOperationNode(assignOperator,variableNode,value);
    }

    private VariableNode parseVariableNode() {
        Token variableToken = code.requireToken(TokenType.NAME);
        return new VariableNode(variableToken);
    }


}
