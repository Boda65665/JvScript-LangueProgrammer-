package org.example.Parser.ParsersPartsCodeTests;
import org.example.AST.*;
import org.example.Parser.GeneratorTestData;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserFormula;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ParserFormulaTest {
    private final GeneratorTestData generatorTestData = new GeneratorTestData();

    @Test
    public void parseFormula() {
        BindOperationNode formula = buildDateForTestFormula();
        testParseLeftFormula((BindOperationNode) formula.getLeftNode());
        testParseRightFormula((BindOperationNode)formula.getRightNode());
    }

    private BindOperationNode buildDateForTestFormula() {
        UnarOperationNode log = generateTestData("log((2+3)+3*4);");
        ArgumentNode argumentLog = (ArgumentNode) log.getOperand();
        return (BindOperationNode) argumentLog.getAllArgs()[0];
    }

    private UnarOperationNode generateTestData(String code) {
        return (UnarOperationNode) generatorTestData.generate(new ParserFormula(generatorTestData.generateCode(code),new BufferFunctions()));
    }

    private void testParseLeftFormula(BindOperationNode formula) {
        ValueNode leftValue = (ValueNode) formula.getLeftNode();
        ValueNode rightValue = (ValueNode) formula.getRightNode();

        Assertions.assertEquals("2", leftValue.getToken().text());
        Assertions.assertEquals("+", formula.getToken().text());
        Assertions.assertEquals("3", rightValue.getToken().text());
    }
    private void testParseRightFormula(BindOperationNode formula){
        ValueNode leftValue = (ValueNode) formula.getLeftNode();
        ValueNode rightValue = (ValueNode) formula.getRightNode();

        Assertions.assertEquals("3", leftValue.getToken().text());
        Assertions.assertEquals("*", formula.getToken().text());
        Assertions.assertEquals("4", rightValue.getToken().text());
    }
}
