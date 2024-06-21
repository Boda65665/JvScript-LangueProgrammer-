package org.example.AST;

import org.example.Entiy.BodyClass;
import org.example.Entiy.Token;

public class ClassNode extends ExpressionNode{

    private BodyClass bodyClass;

    public ClassNode(Token token) {
        super(token);
    }

    public BodyClass getBodyClass() {
        return bodyClass;
    }

    public void setBodyClass(BodyClass bodyClass) {
        this.bodyClass = bodyClass;
    }
}
