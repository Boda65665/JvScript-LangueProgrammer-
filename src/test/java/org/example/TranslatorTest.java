package org.example;//package org.example;

import org.example.Compiler.CompilerTest;
import org.example.Parser.ParserCodeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CompilerTest.class, ParserCodeTest.class})
public class TranslatorTest {
}
