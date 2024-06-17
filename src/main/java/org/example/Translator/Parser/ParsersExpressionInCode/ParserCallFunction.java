package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.ArgumentNode;
import org.example.AST.ExpressionNode;
import org.example.AST.UnarOperationNode;
import org.example.Entiy.Code;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Exception.ParseError;
import org.example.Translator.BufferFunctions;
import org.example.Translator.Parser.ParserBase;

public class ParserCallFunction extends ParserBase {
    private final ParserParentChess parseParentheses;
    private final BufferFunctions bufferFunctions;

    public ParserCallFunction(Code code, BufferFunctions bufferFunctions) {
        super(code);
        parseParentheses=new ParserParentChess(code,bufferFunctions);
        this.bufferFunctions = bufferFunctions;
    }

    @Override
    public ExpressionNode parse() throws ParseError {
        Token token = code.requireToken(TokenType.NAME);
        ArgumentNode argumentNode = (ArgumentNode) parseParentheses.parse();
        validCallFunction(token.text(),argumentNode.getSizeArguments());
        return new UnarOperationNode(token,argumentNode);
    }


    private void validCallFunction(String nameFunc,int sizeArg) throws ParseError {
        String[] namesArg  = bufferFunctions.getNamesArgumentFunction(nameFunc);
        int exceptedArgSize = namesArg.length;
        if(exceptedArgSize!=sizeArg) {
            throw new ParseError("неверное количество аргументов в функции %s,ожидалось %d.",code.getPosCurrentToken());
        }
    }
}
