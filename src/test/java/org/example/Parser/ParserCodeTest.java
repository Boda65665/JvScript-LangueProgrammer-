package org.example.Parser;

import org.example.Parser.ParsersPartsCodeTests.*;
import org.example.Translator.Parser.ParsersExpressionInCode.ParserVariableChange;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ParserCallFunctionTest.class, ParserCreateFunctionTest.class, ParserFormulaTest.class
        , ParserLogicalBranchingTest.class, ParserValueTest.class, ParserValueTest.class, ParserVariableChangeTest.class})
public class ParserCodeTest {
}












