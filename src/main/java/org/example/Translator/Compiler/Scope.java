package org.example.Translator.Compiler;

import org.example.AST.BindOperationNode;
import org.example.AST.ExpressionNode;
import org.example.AST.VariableNode;
import org.example.Entiy.Position;
import org.example.Entiy.Token;
import org.example.Entiy.ValueType;
import org.example.Entiy.Variable;
import org.example.Exception.CompilationError;


import java.util.HashMap;

public class Scope {
    private final HashMap<String, Variable> globalScope = new HashMap<>();
    private HashMap<String,Variable> localScope = null;
    private final Compiler compiler;

    public Scope(Compiler compiler) {
        this.compiler = compiler;
    }

    public void setLocalVisible() {
        localScope=new HashMap<>();
    }

    public void setGlobalVisible() {
        localScope=null;
    }

    public void setVariable(ExpressionNode node) {
        BindOperationNode variableCreationNode = (BindOperationNode) node;
        VariableNode variableNode = (VariableNode) variableCreationNode.getLeftNode();
        Object valueVar = compiler.compile(variableCreationNode.getRightNode());
        Variable variable = new Variable(valueVar,variableNode.getValueType());
        if (!isRightTypeData(variableNode.getValueType(),variable.getValue())) {
            throw new CompilationError(String.format("у переменной %s ожиданмый тип %s ", variableNode.getToken().text(), variableNode.getValueType()), node.getPosition());
        }
        Token varTokenName = variableNode.getToken();
        String nameVar = varTokenName.text();
        if (localScope!=null) setLocalVar(nameVar,variable);
        else setGlobalVar(nameVar,variable);
    }

    public void setLocalVar(String nameVar, Variable variable) {
        if (localScope==null)localScope=new HashMap<>();
        localScope.put(nameVar,variable);
    }

    public void setGlobalVar(String nameVar, Variable variable) {
        globalScope.put(nameVar,variable);
    }

    public static boolean isRightTypeData(ValueType type, Object value) {
        return switch (type) {
            case INT -> value instanceof Integer;
            case DOUBLE -> value instanceof Double;
            case STRING -> value instanceof String;
            case BOOL -> value instanceof Boolean;
            default -> false;
        };
    }

    public ValueType getTypeVar(String nameVar) {
        if (localScope!=null && localScope.get(nameVar)!=null) {
            Variable localVar = localScope.get(nameVar);
            return localVar.getType();
        }
        Variable globalVar = globalScope.get(nameVar);
        return (globalVar==null)?null:globalVar.getType();
    }

    public Object getValueFromVariable(String nameVariable, Position position) {
        if (localScope!=null) {
            Variable localVar = (Variable) getLocalVar(nameVariable);
            if (localVar!=null)return localVar.getValue();
        }
        if (globalScope.get(nameVariable)==null) {
            throw new CompilationError("была вызвана переменная ,которой не существует",position);
        }
        Variable globalVar = globalScope.get(nameVariable);
        return globalVar.getValue();
    }

    private Object getLocalVar(String nameVariable) {
        if (localScope.get(nameVariable)==null)return null;
        return localScope.get(nameVariable);
    }
}
