package org.example.Compiler.CompilersOperationsTests;

import org.example.AST.*;
import org.example.Compiler.HelperForCompilatorsTest;
import org.example.Entiy.*;
import org.example.Exception.CompilationError;
import org.example.Translator.BufferFunctions;
import org.example.Translator.Compiler.Compiler;
import org.example.Translator.Compiler.CompilerOperations.CompilerCallFunction;
import org.example.Translator.Compiler.Scope;
import org.example.Translator.Parser.ParserBase;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserCallFunction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CompilerCallFunctionTest {
    private final Compiler compiler = new Compiler();
    private final BufferFunctions bufferFunctions = HelperForCompilatorsTest.getBufferFunc(compiler);
    private final Scope scope = HelperForCompilatorsTest.getScope(compiler);
    private final CompilerCallFunction compilerCallFunction = new CompilerCallFunction(bufferFunctions,scope,compiler);
    private final HelperForCompilatorsTest helperForCompilatorTest = new HelperForCompilatorsTest(compilerCallFunction);

    public CompilerCallFunctionTest() throws NoSuchFieldException, IllegalAccessException {
    }

    @Test
    public void testReturningFunction() {
        generateReturnableFunc();
        Assertions.assertEquals(20, getReturnValueFunc("sum(12,8)"));
        generateVoidFunc();
        Assertions.assertNull(getReturnValueFunc("testFunc();"));
    }

    private void generateReturnableFunc(){
        Token tokenNameFunc = new Token(TokenType.NAME,"sum",new Position());
        FuncNode funcNode = new FuncNode(tokenNameFunc, ValueType.INT,generateArgumentsFunc(tokenNameFunc), generateBodyFunc());
        bufferFunctions.putFunction("sum",funcNode);
        bufferFunctions.putNamesArgumentsToBuffer("sum",new String[]{"a","b"});
    }

    private ArgumentExceptedNode generateArgumentsFunc(Token tokenNameFunc) {
        ArgumentExceptedNode argumentExceptedNode = new ArgumentExceptedNode(tokenNameFunc);
        argumentExceptedNode.addArg(ValueType.INT,"a");
        argumentExceptedNode.addArg(ValueType.INT,"b");
        return argumentExceptedNode;
    }

    private BodyFunc generateBodyFunc() {
        Token tokenArithmeticOperator = new Token(TokenType.PLUS,"+",new Position());
        VariableNode firstOperand = new VariableNode(new Token(TokenType.NAME,"a",new Position()));
        VariableNode secondOperand = new VariableNode(new Token(TokenType.NAME,"b",new Position()));
        BindOperationNode returnData = new BindOperationNode(tokenArithmeticOperator,firstOperand,secondOperand);
        return new BodyFunc(new StatementsNode(),returnData);
    }

    private void generateVoidFunc(){
        Token tokenNameFunc = new Token(TokenType.NAME,"testFunc",new Position());
        FuncNode funcNode = new FuncNode(tokenNameFunc, null,new ArgumentExceptedNode(tokenNameFunc), new BodyFunc(new StatementsNode(),null));
        bufferFunctions.putFunction("testFunc",funcNode);
        bufferFunctions.putNamesArgumentsToBuffer("testFunc",new String[]{});
    }

    @Test
    public void testValidTypeArg() {
        generateReturnableFunc();
        validIncorrectType();
        validCorrectType();
    }

    private void validIncorrectType() {
        Assertions.assertThrows(CompilationError.class, () -> compile("sum(1.1,2.1);"));
    }

    private void compile(String codeString) {
        helperForCompilatorTest.compile(generateParserBase(codeString));
    }

    private void validCorrectType() {
        Assertions.assertDoesNotThrow(() -> compile("sum(1,2);"));
    }

    @Test
    public void printTest() {
        Assertions.assertNull(getReturnValueFunc("log(13);"));
        Assertions.assertEquals("13",getOutputFromCompileCode("log(13);"));
    }

    private String getOutputFromCompileCode(String code) {
        return helperForCompilatorTest.getOutputFromCompileCode(generateParserBase(code));
    }

    private ParserBase generateParserBase(String code){
        return new ParserCallFunction(helperForCompilatorTest.generateCodeFromStringCode(code),bufferFunctions);
    }

    @Test
    public void inputTest() {
        System.setIn(new java.io.ByteArrayInputStream("testInputText".getBytes()));
        Assertions.assertEquals("testInputText", getReturnValueFunc("input(string);"));
    }

    private Object getReturnValueFunc(String code) {
        return helperForCompilatorTest.generateTestData(generateParserBase(code));
    }
}
