package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.*;
import org.example.Entiy.BodyClass;
import org.example.Entiy.Code;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Exception.ParseError;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Parser.ParserBase;
import org.example.Translator.Parser.ParserCode;

public class ParserClass extends ParserBase {
    private final ParserArgumentExcepted parserArgumentExcepted;
    private BufferFunctions bufferFunctions;
    private ClassNode classNode;

    public ParserClass(Code code) {
        super(code);
        parserArgumentExcepted=new ParserArgumentExcepted(code);
    }

    @Override
    public ExpressionNode parse() throws ParseError {
        code.requireToken(TokenType.CLASS);
        Token tokenNameClass = code.requireToken(TokenType.NAME);
        classNode = new ClassNode(tokenNameClass);
        code.requireToken(TokenType.LCRAP);
        BodyClass bodyClass = parseBodyClass();
        classNode.setBodyClass(bodyClass);
        code.requireToken(TokenType.RCRAP);
        return classNode;
    }

    private BodyClass parseBodyClass() {
        BodyClass bodyClass = new BodyClass();
        bufferFunctions=bodyClass.getBufferFunctions();
        ParserCreateFunction parserCreateFunction = new ParserCreateFunction(code, bufferFunctions);
        ParserVariableCreate parserVariableCreate = new ParserVariableCreate(code, bufferFunctions);
        while (code.isRemainedToken() && !code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(), TokenType.RCRAP)) {
            if (isFindConstructor()){
                bodyClass.setConstructorNode(parseConstructor());
            }
            if (code.isFindTokenOnPosFromTokens(code.getIndexCurrentToken(), TokenType.TYPE)) {

                if (code.isCreateFunctionOnPos(code.getIndexCurrentToken() + 1)) {
                    bodyClass.addFunc((FuncNode) parserCreateFunction.parse());
                }
                else bodyClass.addVar((BindOperationNode) parserVariableCreate.parse());
            }
            code.requireToken(TokenType.END);
        }
        return bodyClass;
    }

    private boolean isFindConstructor() {
        return classNode.getToken().text().equals(code.getCurrentToken().text());
    }

    private ConstructorNode parseConstructor() {
        ParserCode parserCode = new ParserCode(code, bufferFunctions);
        Token tokenConstructor = code.requireToken(TokenType.NAME);
        ArgumentExceptedNode argumentExceptedNode = parserArgumentExcepted();
        code.requireToken(TokenType.LCRAP);
        StatementsNode statementsNode = (StatementsNode) parserCode.parse(TokenType.RCRAP);
        return new ConstructorNode(tokenConstructor,argumentExceptedNode,statementsNode);
    }

    private ArgumentExceptedNode parserArgumentExcepted() {
        code.requireToken(TokenType.LRAP);
        ArgumentExceptedNode argumentExceptedNode = (ArgumentExceptedNode) parserArgumentExcepted.parse();
        code.requireToken(TokenType.RRAP);
        return argumentExceptedNode;
    }
}
