package org.example;

import org.example.Translator.Compiler.Compiler;

public class Appl {

    public static void main(String[] args) {

        String code = """
           int h = 13;int test(int a){return a};
           log(test(h));
           """;
        Compiler compiler = new Compiler(code);
        compiler.compile();
    }
}




