package org.example.Compiler.CompilersOperationsTests;

import org.example.Compiler.HelperForCompilatorsTest;
import org.example.Exception.CompilationError;
import org.example.Exception.ParseError;
import org.example.Translator.BufferFunctions;
import org.example.Translator.Compiler.CompilerOperations.CompilerCreateFunction;
import org.example.Translator.Parser.ParserBase;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserCreateFunction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CompilerCreateFunctionTest {
    private final BufferFunctions bufferFunctions = new BufferFunctions();
    private final CompilerCreateFunction compilerCreateFunction =new CompilerCreateFunction(bufferFunctions);
    private final HelperForCompilatorsTest testHelper = new HelperForCompilatorsTest(compilerCreateFunction);

    @Test
    public void createFuncTest() {
        compile("int testFunc(){return 2};");
    }

    private void compile(String code) throws ParseError, CompilationError {
        testHelper.compile(generateParserBase(code));
    }

    private ParserBase generateParserBase(String code) {
        return new ParserCreateFunction(testHelper.generateCodeFromStringCode(code),bufferFunctions);
    }

    @Test
    public void testAddingFunctionToFunctionPool() {
        compile("int testFunc(){return 2};");
        Assertions.assertNotNull(bufferFunctions.getFunction("testFunc"));
    }
}
