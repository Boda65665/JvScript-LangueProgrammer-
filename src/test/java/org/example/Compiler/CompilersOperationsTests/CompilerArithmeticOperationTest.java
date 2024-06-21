package org.example.Compiler.CompilersOperationsTests;

import org.example.Compiler.HelperForCompilatorsTest;
import org.example.Exception.CompilationError;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Compiler.Compiler;
import org.example.Translator.Compiler.CompilerOperations.CompilerArithmeticOperation;
import org.example.Translator.Parser.ParserBase;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserFormula;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CompilerArithmeticOperationTest {
    private final CompilerArithmeticOperation compilerArithmeticOperationtest = new CompilerArithmeticOperation(new Compiler());
    private final HelperForCompilatorsTest helperForCompilatorsTest = new HelperForCompilatorsTest(compilerArithmeticOperationtest);
    
    @Test
    public void arithmeticOperationsTest() {
        testAddition();
        testSubtraction();
        testMultiplication();
        testDivide();
        testFormulaWithPrivilegedOperators();
        testFormulaWithParentChess();
    }

    private void testAddition() {
        Assertions.assertEquals(3,generateTestData("1+2"));
    }

    private Object generateTestData(String code) {
        return helperForCompilatorsTest.generateTestData(generateParserBase(code));
    }

    private ParserBase generateParserBase(String code) {
        return new ParserFormula(helperForCompilatorsTest.generateCodeFromStringCode(code),new BufferFunctions());
    }

    private void testSubtraction() {
        Assertions.assertEquals(-1,generateTestData("1-2"));
    }

    private void testMultiplication() {
        Assertions.assertEquals(4,generateTestData("2*2"));
    }

    private void testDivide() {
        Assertions.assertEquals(8,generateTestData("16/2"));
    }

    private void testFormulaWithPrivilegedOperators() {
        Assertions.assertEquals(96,generateTestData("1+4+1*4*3/2*15+1"));
    }

    private void testFormulaWithParentChess() {
        Assertions.assertEquals(12,generateTestData("(2+4)+3*4/(4-2)"));
        Assertions.assertEquals(0,generateTestData("((3+3)+3*3*1)*(0)"));
    }

    @Test
    public void testDivideOnZero() {
        Assertions.assertThrows(CompilationError.class, this::compileDivideOnZero);
    }

    private void compileDivideOnZero() {
        helperForCompilatorsTest.compile(generateParserBase("2/0"));
    }
}
