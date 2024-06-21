package org.example;

import org.example.Translator.Compiler.Compiler;

public class Appl {

    public static void main(String[] args) {

        String code = """
           int g = 4;
           int f(){
           return 2
           };
           int a = g;
           a = f()*4;
           log(a);
           """;
        Compiler compiler = new Compiler(code);
        compiler.compile();
    }
}




