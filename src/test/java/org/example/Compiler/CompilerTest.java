package org.example.Compiler;

import org.example.Compiler.CompilersOperationsTests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CompilerValueTest.class, CompilerValueTest.class, CompilerCreateFunctionTest.class,
        CompilerCallFunctionTest.class, CompilerLogicOperationTest.class, CompilerArithmeticOperationTest.class, CompilerChangeVariable.class})
public class CompilerTest {
}