package org.example.Compiler.CompilersOperationsTests;

import org.example.AST.BindOperationNode;
import org.example.Compiler.HelperForCompilatorsTest;
import org.example.Exception.CompilationError;
import org.example.Exception.ParseError;
import org.example.Translator.BufferFunctions;
import org.example.Translator.Compiler.Compiler;
import org.example.Translator.Compiler.CompilerOperations.CompilerCallVariable;
import org.example.Translator.Compiler.CompilerOperations.CompilerCreateVariable;
import org.example.Translator.Compiler.Scope;
import org.example.Translator.Parser.ParsersExpressionInCode.ParseVariableCall;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserVariableCreate;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CompilerVariableTest {
    private final Scope scope = new Scope(new Compiler());
    private final CompilerCallVariable compilerCallVariable = new CompilerCallVariable(scope);
    private final CompilerCreateVariable compilerCreateVariable = new CompilerCreateVariable(scope);
    private HelperForCompilatorsTest helperForCompilatorsTest;

    @Test
    public void createVariableTest() throws ParseError, CompilationError {
        creatVarTest();
        Assertions.assertNotNull(scope.getValueFromVariable("b",null));
    }

    private void creatVarTest() throws ParseError, CompilationError {
        helperForCompilatorsTest=new HelperForCompilatorsTest(compilerCreateVariable);
        ParserVariableCreate parserVariableCreate =
                new ParserVariableCreate(helperForCompilatorsTest.generateCodeFromStringCode("int b = 10;"),new BufferFunctions());
        BindOperationNode bindOperationNode = (BindOperationNode) parserVariableCreate.parse();
        compilerCreateVariable.compile(bindOperationNode);
    }

    @Test
    public void callVariableTest() throws ParseError, CompilationError {
        creatVarTest();
        helperForCompilatorsTest=new HelperForCompilatorsTest(compilerCallVariable);
        ParseVariableCall parseVariableCall =new ParseVariableCall(helperForCompilatorsTest.generateCodeFromStringCode("b"));
        Assertions.assertEquals(10,compilerCallVariable.compile(parseVariableCall.parse()));
    }
}
