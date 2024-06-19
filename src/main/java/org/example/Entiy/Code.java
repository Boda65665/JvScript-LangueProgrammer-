package org.example.Entiy;

import org.example.Exception.ParseError;

import java.util.Arrays;
import java.util.List;

public class Code {
    private int posToken = 0;
    private final List<Token> tokens;
    private final TokenType[] VALUE_TOKENS = new TokenType[]{TokenType.NAME,TokenType.DOUBLE,TokenType.INTEGER,TokenType.STRING,TokenType.TYPE};
    private final TokenType[] ARITHMETIC_OPERATORS = new TokenType[]{TokenType.MINUS,TokenType.PLUS,TokenType.MULTIPLY,TokenType.DIVIDE};
    public Code(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Token requireToken(TokenType... expectedTokenType) throws ParseError {
        Token token = tokenMatch(expectedTokenType);
        if (token==null) throw new ParseError(String.format("ожидается %s", Arrays.toString(expectedTokenType)),getPosCurrentToken());
        return token;
    }

    public Token tokenMatch(TokenType... expectedTokenType){
        List<TokenType> expectedTokenTypeList = Arrays.asList(expectedTokenType);
        if (isRemainedToken()){
            Token token = getCurrentToken();
            if (expectedTokenTypeList.contains(token.type())) {
                nextToken();
                return token;
            }
        }
        return null;
    }

    public boolean isRemainedToken(){
        return posToken<tokens.size();
    }

    public Token getCurrentToken(){
        return tokens.get(posToken);
    }

    public void nextToken(){
        posToken++;
    }

    public Position getPosCurrentToken(){
        return tokens.get(posToken).position();
    }

    public int getIndexCurrentToken(){
        return posToken;
    }

    public Token getTokenOnPos(int index){
        return tokens.get(index);
    }

    public int getCountToken(){
        return tokens.size();
    }

    private boolean isFunctionOnPos(int pos){
        boolean isFindNameFunc = isFindTokenOnPosFromTokens(pos, TokenType.NAME);
        boolean isFindLRAP = isFindTokenOnPosFromTokens(pos + 1, TokenType.LRAP);
        return isFindNameFunc && isFindLRAP;
    }

    public boolean isFindTokenOnPosFromTokens(int pos, TokenType... tokenType) {
        if (pos>=tokens.size())return false;
        return Arrays.asList(tokenType).contains(getTokenOnPos(pos).type());
    }

    public boolean isCreateFunctionOnPos(int pos) {
        if (pos+1>=getCountToken() || pos-1<0) return false;
        boolean isFindTypeFunc = isFindTokenOnPosFromTokens(pos-1,TokenType.TYPE,TokenType.VOID);
        return isFindTypeFunc && isFunctionOnPos(pos);
    }

    public boolean isCallFunctionOnPos(int pos) {
        if (pos+1>=getCountToken() || pos<0) return false;
        boolean isNotFindTypeFunc = (pos - 1 < 0 || !isFindTokenOnPosFromTokens(pos - 1, TokenType.TYPE,TokenType.VOID));
        return isNotFindTypeFunc && isFunctionOnPos(pos);
    }

    public boolean isFindLogicOperatorOnCurrentPos() {
        boolean isFoundBranchOperator = getIndexCurrentToken()>=2 && isFindTokenOnPosFromTokens(getIndexCurrentToken()-2,TokenType.BRANCH_OPERATOR);
        boolean isFoundCompressionOperator= isRemainedToken() && isFindTokenOnPosFromTokens(getIndexCurrentToken()+1,TokenType.COMPRESSION_OPERATOR);
        return isFoundCompressionOperator || isFoundBranchOperator;
    }

    public boolean isFindFormulaOnCurrentPos(){
        if (posToken+1>=tokens.size()) return false;

        if (!(isFindValueOnPos(posToken) && isFindArithmeticOperatorOnPos(posToken+1)) && !(isFindFormulaWithCallFunc(posToken))){
            if (posToken+2>=tokens.size()) return false;
            return (isFindLRAPOnPos(posToken) && isFindValueOnPos(posToken+1) && isFindArithmeticOperatorOnPos(posToken+2));
        }
        return true;
    }

    private boolean isFindValueOnPos(int posToken) {
        return isFindTokenOnPosFromTokens(posToken,VALUE_TOKENS);
    }

    public boolean isFindArithmeticOperatorOnPos(int posToken) {
        return isFindTokenOnPosFromTokens(posToken,ARITHMETIC_OPERATORS);
    }

    private boolean isFindFormulaWithCallFunc(int posToken) {
        if (posToken+3>=tokens.size())return false;
        return isFindTokenOnPosFromTokens(posToken,TokenType.NAME) && isFindArithmeticOperatorOnPos(posToken+3);
    }

    private boolean isFindLRAPOnPos(int posToken) {
        return isFindTokenOnPosFromTokens(posToken,TokenType.LRAP);
    }

    public boolean isFindLogicExpressionOnPos(int posToken) {
        return isFindLogicExp(posToken) || isFindLogicExpInParentChess(posToken);
    }

    private boolean isFindLogicExp(int posToken) {
        boolean isFindValue = isFindTokenOnPosFromTokens(posToken,VALUE_TOKENS);
        boolean isFindCompressionOperator = isFindTokenOnPosFromTokens(posToken+1,TokenType.COMPRESSION_OPERATOR);
        boolean isFindLogicExp = isFindValue && isFindCompressionOperator;

        boolean isFindBoolValue = isFindTokenOnPosFromTokens(posToken,TokenType.BOOL);
        return isFindLogicExp || isFindBoolValue;
    }
    private boolean isFindLogicExpInParentChess(int posToken){
        int pos = posToken;
        boolean isFindLRAP = isFindLRAPOnPos(pos);
        while (isFindLRAPOnPos(pos)){
            pos++;
        }
        return isFindLRAP && isFindLogicExp(pos);
    }
}
