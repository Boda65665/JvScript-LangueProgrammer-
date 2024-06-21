package org.example.Compiler;

import org.example.AST.ExpressionNode;
import org.example.Entiy.Code;

import org.example.Entiy.BufferFunctions;
import org.example.Translator.Compiler.Compiler;
import org.example.Translator.Compiler.CompilerBase;
import org.example.Entiy.Scope;
import org.example.Translator.Lexer;
import org.example.Translator.Parser.ParserBase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

public class HelperForCompilatorsTest {
    private final CompilerBase compilator;
    public HelperForCompilatorsTest(CompilerBase compilator) {
        this.compilator=compilator;
    }

    public Object generateTestData(ExpressionNode node) {
        return compilator.compile(node);
    }

    public Object generateTestData(ParserBase parser) {
        ExpressionNode node = parser.parse();
        return compilator.compile(node);
    }

    public Code generateCodeFromStringCode(String code) {
        Lexer lexer = new Lexer(code);
        return new Code(lexer.lexAnalysis());
    }

    public void compile(ParserBase parser) {
        ExpressionNode node = parser.parse();
        compilator.compile(node);
    }

    public String getOutputFromCompileCode(ParserBase parserBase) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        compile(parserBase);
        return outputStream.toString().trim();
    }

    public static BufferFunctions getBufferFunc(Compiler compiler) throws NoSuchFieldException, IllegalAccessException {
        Field privateField = Compiler.class.getDeclaredField("bufferFunctions");
        privateField.setAccessible(true);
        return (BufferFunctions) privateField.get(compiler);
    }

    public static Scope getScope(Compiler compiler) throws NoSuchFieldException, IllegalAccessException {
        Field privateField = Compiler.class.getDeclaredField("scope");
        privateField.setAccessible(true);
        return (Scope) privateField.get(compiler);
    }
}
