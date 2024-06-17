package org.example.Compiler.CompilersOperationsTests;

import org.example.AST.BindOperationNode;
import org.example.Compiler.HelperForCompilatorsTest;
import org.example.Translator.Compiler.Compiler;
import org.example.Translator.Compiler.CompilerOperations.CompilerLogicOperation;
import org.example.Translator.Parser.ParserBase;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserLogicBranching;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CompilerLogicOperationTest {
    private final Compiler compiler = new Compiler();
    private final CompilerLogicOperation compilerLogicOperation =
            new CompilerLogicOperation(HelperForCompilatorsTest.getScope(compiler),compiler);
    private final HelperForCompilatorsTest helperForCompilatorsTest = new HelperForCompilatorsTest(compilerLogicOperation);

    public CompilerLogicOperationTest() throws NoSuchFieldException, IllegalAccessException {
    }

    @Test
    public void testCompileLogicExp() throws NoSuchFieldException, IllegalAccessException {
        Assertions.assertTrue(isTrue(getLogicExpNode("false | true")));
        Assertions.assertFalse(isTrue(getLogicExpNode("true & false")));
        Assertions.assertFalse(isTrue(getLogicExpNode("false & false")));
        Assertions.assertTrue(isTrue(getLogicExpNode("((2>1) & (3>1)) & (2>1 & (true |2>1))")));
    }

    private BindOperationNode getLogicExpNode(String logicExp) throws NoSuchFieldException, IllegalAccessException {
        ParserLogicBranching parserLogicBranching = new ParserLogicBranching(helperForCompilatorsTest.generateCodeFromStringCode(logicExp),HelperForCompilatorsTest.getBufferFunc(compiler));
        return (BindOperationNode) parserLogicBranching.parseLogicExpression();
    }

    private boolean isTrue(BindOperationNode logExp) {
        return compilerLogicOperation.isTrueLogicExpression(logExp);
    }

    @Test
    public void testCompileLogicBranch() throws ReflectiveOperationException {
        Assertions.assertEquals("1",getOutputFromCompileCode("if(((2>1) & (3>1)) & (2>1 & (true |2>1))){log(1);};"));
    }

    private String getOutputFromCompileCode(String code) throws ReflectiveOperationException {
        return helperForCompilatorsTest.getOutputFromCompileCode(generateParserBase(code));
    }

    private ParserBase generateParserBase(String code) throws ReflectiveOperationException {
        return new ParserLogicBranching(helperForCompilatorsTest.generateCodeFromStringCode(code),HelperForCompilatorsTest.getBufferFunc(compiler));
    }
}
