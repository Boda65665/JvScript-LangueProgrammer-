package org.example.Entiy;

import org.example.AST.BindOperationNode;
import org.example.AST.ConstructorNode;
import org.example.AST.FuncNode;
import org.example.Translator.Compiler.Compiler;

public class BodyClass {
    private final BufferFunctions bufferFunctions = new BufferFunctions();
    private final Scope scope = new Scope(new Compiler());
    private ConstructorNode constructorNode;

    public FuncNode getFunc(String nameFunc){
        return bufferFunctions.getFunction(nameFunc);
    }

    public void addFunc(FuncNode funcNode){
        bufferFunctions.putFunction(funcNode.getNameFunc(),funcNode);
    }

    public Object getVar(String name, Position position){
        return scope.getValueFromVariable(name,position);
    }

    public void addVar(BindOperationNode variable){
        scope.setVariable(variable);
    }

    public BufferFunctions getBufferFunctions() {
        return bufferFunctions;
    }

    public Scope getScope() {
        return scope;
    }

    public ConstructorNode getConstructorNode() {
        return constructorNode;
    }

    public void setConstructorNode(ConstructorNode constructorNode) {
        this.constructorNode = constructorNode;
    }
}
