package org.example.AST;

import org.example.Entiy.Token;
import org.example.Entiy.ValueType;

public class VariableNode extends ExpressionNode {
    private ValueType valueType;

    public VariableNode(Token variable) {
        super(variable);
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setType(ValueType type) {
        valueType=type;
    }


}
