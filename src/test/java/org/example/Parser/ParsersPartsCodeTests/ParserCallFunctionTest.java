package org.example.Parser.ParsersPartsCodeTests;

import org.example.AST.*;
import org.example.Entiy.ValueType;
import org.example.Parser.GeneratorTestData;
import org.example.Translator.BufferFunctions;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserCallFunction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ParserCallFunctionTest {
    private final GeneratorTestData generatorTestData = new GeneratorTestData();

    @Test
    public void parseCallFunctionTest() {
        UnarOperationNode funcNode = (UnarOperationNode) generateTestData("testFunc(10,21);");
        testCallFunction(funcNode,"testFunc",10,21);
    }

    private Object generateTestData(String code) {
        BufferFunctions bufferFunctions = new BufferFunctions();
        String[] namesArg = new String[]{"a","b"};
        bufferFunctions.putNamesArgumentsToBuffer("testFunc",namesArg);
        ParserCallFunction parserCallFunction =new ParserCallFunction(generatorTestData.generateCode(code),bufferFunctions);
        return generatorTestData.generate(parserCallFunction);
    }

    private void testCallFunction(UnarOperationNode funcNode,String exceptedName,Object... exceptedArg) {
        testParseName(funcNode,exceptedName);
        testArgCallFunction((ArgumentNode) funcNode.getOperand(),exceptedArg);
    }

    private void testParseName(UnarOperationNode node,String exceptedName) {
        String nameFunc = node.getToken().text();
        Assertions.assertEquals(nameFunc,exceptedName);
    }

    private void testArgCallFunction(ArgumentNode argumentNode,Object... exceptedArg) {
        ExpressionNode[] args = argumentNode.getAllArgs();
        Assertions.assertEquals(args.length,exceptedArg.length);
        for (int i = 0; i < args.length; i++) {
            ValueNode argNode = (ValueNode) args[i];
            String argString = argNode.getToken().text();
            Assertions.assertTrue(String.valueOf(exceptedArg[i]).equalsIgnoreCase(argString));
        }
    }

    @Test
    public void parseInput() {
        UnarOperationNode inputNode = (UnarOperationNode) generateTestData("input(double);");
        testCallFunction(inputNode,"input", ValueType.DOUBLE);
    }

    @Test
    public void parsePrint() {
        UnarOperationNode printNode = (UnarOperationNode) generateTestData("log(1);");
        testCallFunction(printNode,"log",1);
    }
}
