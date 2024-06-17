package org.example.AST;

import org.example.Entiy.Token;
import org.example.Entiy.ValueType;

import java.util.HashMap;

public class ArgumentExceptedNode extends ExpressionNode{
    private final HashMap<String, ValueType> arguments = new HashMap<>();
    int sizeArg = 0;

    public ArgumentExceptedNode(Token token) {
        super(token);
    }

    public void addArg(ValueType exceptedType,String name){
        arguments.put(name,exceptedType);
        sizeArg++;
    }

    public int getSizeArg() {
        return sizeArg;
    }

    public ValueType getExceptedArg(String nameArg){
        return arguments.get(nameArg);
    }

    public String[] getAllNameArguments() {
        return arguments.keySet().toArray(new String[sizeArg]);
    }
}
