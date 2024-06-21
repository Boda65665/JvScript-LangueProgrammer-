package org.example.Parser.ParsersPartsCodeTests;

import org.example.AST.ClassNode;
import org.example.Entiy.BodyClass;
import org.example.Entiy.Position;
import org.example.Parser.GeneratorTestData;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ParserClassTest {

    private final GeneratorTestData generatorTestData = new GeneratorTestData();
    private ParserClass parserClassTest;

    @Test
    public void testAddingFunction(){
        ClassNode classNode = generateClassWithFunction();
        BodyClass bodyClass = classNode.getBodyClass();
        Assertions.assertNotNull(bodyClass.getFunc("a"));
    }

    private ClassNode generateClassWithFunction() {
        String code = """
                class User{
                    int a(){
                return 2};
                }
                """;
        return generateTestData(code);
    }

    private ClassNode generateTestData(String code){
        parserClassTest=new ParserClass(generatorTestData.generateCode(code));
        return (ClassNode) parserClassTest.parse();
    }

    @Test
    public void testAddingVar(){
        ClassNode classNode = generateClassWithVar();
        BodyClass bodyClass = classNode.getBodyClass();
        Assertions.assertEquals(2,bodyClass.getVar("user",new Position()));
    }

    private ClassNode generateClassWithVar() {
        String code = """
                class User{
                int user = 2;
                }
                """;
        return generateTestData(code);
    }

    @Test
    public void testSetConstructor(){
        ClassNode classNode = generateClassWithConstructor();
        BodyClass bodyClass = classNode.getBodyClass();
        Assertions.assertNotNull(bodyClass.getConstructorNode());
        Assertions.assertEquals(3,bodyClass.getVar("a",new Position()));
    }

    private ClassNode generateClassWithConstructor() {
        String code = """
                class User{
                int a = 3;
                User(){
                a=4;
                };
                }
                """;
        return generateTestData(code);
    }

}

