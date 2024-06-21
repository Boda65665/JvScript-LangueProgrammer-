package org.example.Translator.Parser.ParsersExpressionInCode;

import org.example.AST.ExpressionNode;
import org.example.Entiy.Code;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Parser.ParserBase;

public class ParserDataAndFormula extends ParserBase {
    private final ParserFormula parserFormula;
    private final ParserData parserData;

    public ParserDataAndFormula(Code code, BufferFunctions bufferFunctions) {
        super(code);
        parserFormula =new ParserFormula(code,bufferFunctions);
        parserData=new ParserData(code,bufferFunctions);
    }

    @Override
    public ExpressionNode parse() {
        if (code.isFindFormulaOnCurrentPos()) {
            return parserFormula.parse();
        }
        return parserData.parse();
    }
}
