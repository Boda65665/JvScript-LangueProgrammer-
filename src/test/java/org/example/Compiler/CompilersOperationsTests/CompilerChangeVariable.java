package org.example.Compiler.CompilersOperationsTests;

import org.example.AST.BindOperationNode;
import org.example.AST.ValueNode;
import org.example.AST.VariableNode;
import org.example.Compiler.HelperForCompilatorsTest;
import org.example.Entiy.*;
import org.example.Parser.GeneratorTestData;
import org.example.Translator.Compiler.Compiler;
import org.example.Translator.Compiler.CompilerOperations.CompilerCreateVariable;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserVariableChange;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CompilerChangeVariable {
    private final Compiler compiler = new Compiler();
    private final Scope scope = HelperForCompilatorsTest.getScope(compiler);
    private final CompilerCreateVariable compilerCreateVariable = new CompilerCreateVariable(scope);

    public CompilerChangeVariable() throws NoSuchFieldException, IllegalAccessException {

    }

    @Test
    public void testChangeVar(){
        createVar();
        Assertions.assertEquals(3,scope.getValueFromVariable("test",new Position()));
        changeVar();
        Assertions.assertEquals(353,scope.getValueFromVariable("test",new Position()));
    }

    private void createVar(){
        Token tokenValue = new Token(TokenType.INTEGER,"3",new Position());
        ValueNode valueNode = new ValueNode(tokenValue);
        Token tokenNameVar = new Token(TokenType.NAME,"test",new Position());
        Token operator = new Token(TokenType.ASSIGN,"=",new Position());
        VariableNode variableNode = new VariableNode(tokenNameVar);
        variableNode.setType(ValueType.INT);
        BindOperationNode nodeCreateVar = new BindOperationNode(operator,variableNode,valueNode);
        compilerCreateVariable.compile(nodeCreateVar);
    }

    private void changeVar() {
        Token tokenValue = new Token(TokenType.INTEGER,"353",new Position());
        ValueNode valueNode = new ValueNode(tokenValue);
        Token tokenNameVar = new Token(TokenType.NAME,"test",new Position());
        Token operator = new Token(TokenType.ASSIGN,"=",new Position());
        VariableNode variableNode = new VariableNode(tokenNameVar);
        BindOperationNode nodeCreateVar = new BindOperationNode(operator,variableNode,valueNode);
        compilerCreateVariable.compile(nodeCreateVar);
    }
}
