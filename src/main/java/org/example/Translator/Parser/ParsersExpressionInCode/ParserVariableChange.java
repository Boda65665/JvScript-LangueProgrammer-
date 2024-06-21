package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.BindOperationNode;
import org.example.AST.ExpressionNode;
import org.example.AST.UnarOperationNode;
import org.example.AST.VariableNode;
import org.example.Entiy.*;
import org.example.Exception.ParseError;
import org.example.Translator.Parser.ParserBase;

public class ParserVariableChange extends ParserBase {
    private final ParserDataAndFormula parserDataAndFormula;
    private final BufferFunctions bufferFunctions;
    public ParserVariableChange(Code code, BufferFunctions bufferFunctions) {
        super(code);
        parserDataAndFormula = new ParserDataAndFormula(code,bufferFunctions);
        this.bufferFunctions = bufferFunctions;
    }

    @Override
    public ExpressionNode parse() throws ParseError {
        Token tokenNameVar = code.requireToken(TokenType.NAME);
        Token tokenASSIGN = code.requireToken(TokenType.ASSIGN);
        VariableNode variableNode = new VariableNode(tokenNameVar);
        ExpressionNode value = parserDataAndFormula.parse();
        return new BindOperationNode(tokenASSIGN,variableNode,value);
    }
}
