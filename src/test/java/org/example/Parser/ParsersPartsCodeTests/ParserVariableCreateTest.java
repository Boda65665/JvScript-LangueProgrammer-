package org.example.Parser.ParsersPartsCodeTests;

import org.example.AST.BindOperationNode;
import org.example.AST.ValueNode;
import org.example.AST.VariableNode;
import org.example.Entiy.ValueType;
import org.example.Parser.GeneratorTestData;
import org.example.Translator.BufferFunctions;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserVariableCreate;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ParserVariableCreateTest {
    private final GeneratorTestData generateTestData = new GeneratorTestData();

    @Test
    public void parseVariableTest() {
        BindOperationNode variable = generateTestData();
        ValueNode valueNode = (ValueNode) variable.getRightNode();
        VariableNode variableNode = (VariableNode) variable.getLeftNode();

        Assertions.assertEquals(ValueType.INT,variableNode.getValueType());
        Assertions.assertEquals("h",variableNode.getToken().text());
        Assertions.assertEquals("=", variable.getToken().text());
        Assertions.assertEquals("4", valueNode.getToken().text());
    }

    private BindOperationNode generateTestData() {
        return (BindOperationNode) generateTestData.generate(new ParserVariableCreate(generateTestData.generateCode("int h = 4;"),new BufferFunctions()));
    }
}
