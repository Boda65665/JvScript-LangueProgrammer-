package org.example.Compiler.CompilersOperationsTests;

import org.example.AST.ValueNode;
import org.example.Compiler.HelperForCompilatorsTest;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Translator.Compiler.CompilerOperations.CompilerValue;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CompilerValueTest {
    private final HelperForCompilatorsTest helperForCompilatorsTest = new HelperForCompilatorsTest(new CompilerValue());

    @Test
    public void testStringValue() {
        Token stringToken = new Token(TokenType.STRING, """
                "weffew
                """,null);
        Assertions.assertEquals("weffew",compileValue(stringToken));
    }

    private Object compileValue(Token token) {
        return helperForCompilatorsTest.generateTestData(new ValueNode(token));
    }

    @Test
    public void testNumberValue() {
        Assertions.assertEquals(4,(Integer) compileValue(new Token(TokenType.INTEGER,"4",null)));
        Assertions.assertEquals(4.4,(Double) compileValue(new Token(TokenType.DOUBLE,"4.4",null)));
    }

    @Test
    public void testBoolValue() {
        Assertions.assertEquals(true,compileValue(new Token(TokenType.BOOL,"true",null)));
        Assertions.assertEquals(false,compileValue(new Token(TokenType.BOOL,"false",null)));
    }
}
