package org.example.AST;

import org.example.Entiy.Position;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;

public class ExpressionNode {
    protected final Token token;

    public ExpressionNode(Token token) {
        this.token = token;
    }

    public Position getPosition(){
        return token.position();
    };

    public TokenType getTokenType(){
        return token.type();
    }

    public Token getToken(){
        return token;
    }


}
