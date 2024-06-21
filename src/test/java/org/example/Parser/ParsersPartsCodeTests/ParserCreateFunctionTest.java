package org.example.Parser.ParsersPartsCodeTests;

import org.example.AST.*;
import org.example.Entiy.BodyFunc;
import org.example.Entiy.Token;
import org.example.Entiy.ValueType;
import org.example.Parser.GeneratorTestData;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserCreateFunction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;



public class ParserCreateFunctionTest {
    private final GeneratorTestData generateTestData = new GeneratorTestData();

    @Test
    public void testParseArgument() {
        FuncNode funcNode = generateTestData("int sum(int a,double b){log(a);return a+b};");
        ArgumentExceptedNode argumentExceptedNode = funcNode.getArgumentExceptedNode();
        Assertions.assertEquals(argumentExceptedNode.getExceptedArg("a"), ValueType.INT);
        Assertions.assertEquals(argumentExceptedNode.getExceptedArg("b"),ValueType.DOUBLE);
    }

    private FuncNode generateTestData(String code) {
        return (FuncNode) generateTestData.generate(new ParserCreateFunction(generateTestData.generateCode(code),new BufferFunctions()));
    }

    @Test
    public void testParseReturnType() {
        FuncNode funcNode = generateTestData("int sum(){return 0};");
        Assertions.assertEquals(funcNode.getReturnType(),ValueType.INT);
        funcNode = generateTestData("void test(){};");
        Assertions.assertNull(funcNode.getReturnType());

    }

    @Test
    public void testParseBodyFunc() {
        FuncNode funcNode = generateTestData("int sum(int a,double b){log(a);return a+b};");
        BodyFunc bodyFunc = funcNode.getBodyFunc();
        StatementsNode codeInBodyFunc = bodyFunc.code();
        UnarOperationNode logNode = (UnarOperationNode) codeInBodyFunc.getNodes().get(0);
        testParseOperationInBodyFunc(logNode);

        BindOperationNode returnNode = (BindOperationNode) bodyFunc.returnedData();
        testParseReturnInBodyFunc(returnNode);
    }

    private void testParseOperationInBodyFunc(UnarOperationNode logNode) {
        Token operator = logNode.getToken();
        ArgumentNode argsLog = (ArgumentNode) logNode.getOperand();
        VariableNode argLog = (VariableNode) argsLog.getArg("valueLog");

        Assertions.assertEquals(operator.text(),"log");
        Assertions.assertEquals(argLog.getToken().text(),"a");
    }

    private void testParseReturnInBodyFunc(BindOperationNode returnNode) {
        Assertions.assertInstanceOf(BindOperationNode.class,returnNode);

        VariableNode arg1 = (VariableNode) returnNode.getLeftNode();
        VariableNode arg2 = (VariableNode) returnNode.getRightNode();
        Token arithmeticOperator = returnNode.getToken();
        String nameVar1 = arg1.getToken().text();
        String nameVar2 = arg2.getToken().text();

        Assertions.assertEquals(nameVar1,"a");
        Assertions.assertEquals(arithmeticOperator.text(),"+");
        Assertions.assertEquals(nameVar2,"b");
    }

}
