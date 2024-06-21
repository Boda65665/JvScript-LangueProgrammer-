package org.example.Parser.ParsersPartsCodeTests;

import org.example.AST.BindOperationNode;
import org.example.AST.ValueNode;
import org.example.AST.VariableNode;
import org.example.Entiy.BufferFunctions;
import org.example.Entiy.TokenType;
import org.example.Parser.GeneratorTestData;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserVariableChange;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ParserVariableChangeTest {
    private  ParserVariableChange parserVariableChange;
    private final GeneratorTestData generatorTestData = new GeneratorTestData();

    public ParserVariableChangeTest() {

    }

    @Test
    public void parserTest(){
        String codeString = "a = 3";
        parserVariableChange = new ParserVariableChange(generatorTestData.generateCode(codeString),new BufferFunctions());
        BindOperationNode nodeChangeVariable = (BindOperationNode) parserVariableChange.parse();
        Assertions.assertInstanceOf(VariableNode.class,nodeChangeVariable.getLeftNode());
        Assertions.assertEquals(TokenType.ASSIGN,nodeChangeVariable.getToken().type());
        Assertions.assertInstanceOf(ValueNode.class,nodeChangeVariable.getRightNode());
    }
}
