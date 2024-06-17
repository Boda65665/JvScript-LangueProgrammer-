package org.example.Translator;


import org.example.Entiy.Position;
import org.example.Entiy.Token;
import org.example.Entiy.TokenType;
import org.example.Exception.ParseError;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    String code;
    Position positionInCode = new Position();
    int posInTextCode = 0;
    private final List<Token> tokenList = new ArrayList<>();

    public Lexer(String code) {
        this.code = code;
    }

    public List<Token> lexAnalysis() {
        while (posInTextCode < code.length()) {
            Token token = tokenMatcher();
            if (token!=null){
                if (token.type()==TokenType.LINE_BREAK){
                    positionInCode.lineBreak();
                    posInTextCode++;
                    continue;
                }
                if (token.type()!=TokenType.SPACE) {
                    tokenList.add(token);
                }
                positionInCode.addToPosInLine(token.text().length());
                posInTextCode+=token.text().length();
                continue;
            }
            throw new ParseError("лексическая ошибка",positionInCode);
        }
        return tokenList;
    }

    private Token tokenMatcher() {
        TokenType[] tokenTypes = TokenType.values();
        for (TokenType tokenType : tokenTypes) {
            Pattern pattern = Pattern.compile(getRegExp(tokenType));
            Matcher matcher = pattern.matcher(code.substring(this.posInTextCode));
            if (matcher.find()) return new Token(tokenType, matcher.group(), new Position(positionInCode));
        }
        return null;
    }

    private String getRegExp(TokenType tokenType) {
        String regExp = "^" + tokenType.regex + "+";
        return tokenType == TokenType.LRAP || tokenType == TokenType.RRAP? regExp + "?" :regExp;
    }
}


