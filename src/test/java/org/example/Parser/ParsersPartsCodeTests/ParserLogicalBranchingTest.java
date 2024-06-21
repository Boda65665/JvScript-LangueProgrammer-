package org.example.Parser.ParsersPartsCodeTests;

import org.example.AST.*;
import org.example.Parser.GeneratorTestData;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserLogicBranching;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class ParserLogicalBranchingTest {
    private final GeneratorTestData generatorTestData = new GeneratorTestData();

    @Test
    public void testParseLogicExp() {
        LogicNode logicNode = generateTestData();
        BindOperationNode logicExp = (BindOperationNode) logicNode.getLogicExpression();
        testParseLeftLogicExp((BindOperationNode) logicExp.getLeftNode());
        Assertions.assertEquals("|", logicExp.getToken().text());
        testParseRightLogicExp((BindOperationNode) logicExp.getRightNode());
    }

    private LogicNode generateTestData() {
        String code = "if(2<1 & 3>2 | 5>1){log(1);}else{log(0);};";
        return (LogicNode) generatorTestData.generate(new ParserLogicBranching(generatorTestData.generateCode(code),new BufferFunctions()));
    }

    private void testParseLeftLogicExp(BindOperationNode leftLogExp) {
        testParseLeftLeftLogicExp((BindOperationNode) leftLogExp.getLeftNode());
        Assertions.assertEquals("&", leftLogExp.getToken().text());
        testParseLeftRightLogicExp((BindOperationNode) leftLogExp.getRightNode());
    }

    private void testParseLeftRightLogicExp(BindOperationNode leftRightLogicExp) {
        ValueNode valueNodeLeft = (ValueNode) leftRightLogicExp.getLeftNode();
        Assertions.assertEquals("3", valueNodeLeft.getToken().text());
        ValueNode valueNodeRight= (ValueNode) leftRightLogicExp.getRightNode();
        Assertions.assertEquals(">", leftRightLogicExp.getToken().text());
        Assertions.assertEquals("2", valueNodeRight.getToken().text());
    }

    private void testParseLeftLeftLogicExp(BindOperationNode leftLeftLogExp) {
        ValueNode valueNodeLeft = (ValueNode) leftLeftLogExp.getLeftNode();
        Assertions.assertEquals("2", valueNodeLeft.getToken().text());
        Assertions.assertEquals("<", leftLeftLogExp.getToken().text());
        ValueNode valueNodeRight = (ValueNode) leftLeftLogExp.getRightNode();
        Assertions.assertEquals("1", valueNodeRight.getToken().text());
    }

    private void testParseRightLogicExp(BindOperationNode rightLogicExp) {
        ValueNode valueNodeLeft = (ValueNode) rightLogicExp.getLeftNode();
        Assertions.assertEquals("5", valueNodeLeft.getToken().text());
        ValueNode valueNodeRight = (ValueNode) rightLogicExp.getRightNode();
        Assertions.assertEquals(">", rightLogicExp.getToken().text());
        Assertions.assertEquals("1", valueNodeRight.getToken().text());
    }

    @Test
    public void testParseBodyLogicBranching() {
        LogicNode logicNode = generateTestData();
        testParseIfBody(logicNode.getIfBody());
        testParseElseBody(logicNode.getElseBody());
    }

    private void testParseIfBody(StatementsNode ifBody) {
        UnarOperationNode logNodeInIfBody = (UnarOperationNode) ifBody.getNodes().get(0);
        ArgumentNode argsLog = (ArgumentNode) logNodeInIfBody.getOperand();
        ValueNode argLog = (ValueNode) argsLog.getArg("valueLog");
        Assertions.assertEquals("1", argLog.getToken().text());
    }

    private void testParseElseBody(StatementsNode elseBody) {
        UnarOperationNode logNodeInElseBody = (UnarOperationNode) elseBody.getNodes().get(0);
        ArgumentNode argsLog = (ArgumentNode) logNodeInElseBody.getOperand();
        ValueNode argLog = (ValueNode) argsLog.getArg("valueLog");
        Assertions.assertEquals("0", argLog.getToken().text());
    }
}

