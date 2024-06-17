package org.example.Entiy;

public class Variable {
    Object value;
    ValueType type;

    public Variable(Object value, ValueType type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public ValueType getType() {
        return type;
    }
}
