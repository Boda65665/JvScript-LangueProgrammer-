package org.example;

import org.example.Translator.Compiler.Compiler;

public class Appl {

    public static void main(String[] args) {

        String code = """
           int testFunc(){
           return 2};
           log((testFunc()*123+3213-231/2
           +(12*31)));
           """;
        Compiler compiler = new Compiler(code);
        compiler.compile();
    }
}




