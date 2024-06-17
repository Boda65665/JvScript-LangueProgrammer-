package org.example.Entiy;

public enum TokenType {
    CLASS("class"),
    COMPRESSION_OPERATOR("(!=|==|>=|<=|>|<)"),
    COMMA(","),
    BOOL("(true|false)"),
    VOID("void"),
    TYPE("(\\bint\\b|\\bdouble\\b|\\bstring\\b|\\bbool\\b)"),
    ASSIGN("\\="),
    RETURN("return"),
    STRING("\"(.*?)\""),
    DOUBLE("\\d+\\.\\d"),
    INTEGER("+[0-9]"),
    BRANCH_OPERATOR("(if|else)"),
    LOGIC_OPERATOR("[\\&,\\|]"),
    END(";"),
    LINE_BREAK("\n"),
    SPACE("[ \\t\\r]"),
    PLUS("\\+"),
    MULTIPLY("\\*"),
    DIVIDE("\\/"),
    MINUS("-"),
    LRAP("\\("),
    RRAP("\\)"),
    LCRAP("\\{"),
    RCRAP("\\}"),
    NAME("[a-zA-Z]");
    public final String regex;

    TokenType(String regex) {
        this.regex = regex;
    }

}
