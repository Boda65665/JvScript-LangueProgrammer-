package org.example.Parser.ParsersPartsCodeTests;

import org.example.AST.ValueNode;
import org.example.Parser.GeneratorTestData;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserValue;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ParserValueTest {
    private final GeneratorTestData generateTestData = new GeneratorTestData();

    @Test
    public void parseValueTest() {
        ValueNode valueNode = generateTestData();
        Assertions.assertEquals("4", valueNode.getToken().text());
    }

    private ValueNode generateTestData() {
        return (ValueNode) generateTestData.generate(new ParserValue(generateTestData.generateCode("4")));
    }
}
