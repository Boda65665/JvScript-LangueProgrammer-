package org.example.Parser;

import org.example.AST.ExpressionNode;
import org.example.Entiy.Code;
import org.example.Translator.Lexer;
import org.example.Translator.Parser.ParserBase;

public class GeneratorTestData {

    public ExpressionNode generate(ParserBase parser) {
        return parser.parse();
    }

    private Lexer generateLexer(String code){
        return new Lexer(code);
    }

    public Code generateCode(String code) {
        return new Code(generateLexer(code).lexAnalysis());
    }
}
