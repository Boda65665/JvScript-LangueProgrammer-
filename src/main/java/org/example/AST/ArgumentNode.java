package org.example.AST;

import org.example.Entiy.Token;

import java.util.HashMap;

public class ArgumentNode extends ExpressionNode{
    private final HashMap<String, ExpressionNode> arguments = new HashMap<>();

    public ArgumentNode(Token token) {
        super(token);
    }

    public void putArg(String nameArg,ExpressionNode argNode) {
        arguments.put(nameArg,argNode);
    }

    public ExpressionNode getArg(String nameArg){
        return arguments.get(nameArg);
    }

    public int getSizeArguments(){
        return arguments.size();
    }

    public ExpressionNode[] getAllArgs(){
        return arguments.values().toArray(new ExpressionNode[0]);
    }
}
