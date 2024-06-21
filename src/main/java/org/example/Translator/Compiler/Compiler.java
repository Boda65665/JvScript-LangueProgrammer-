package org.example.Translator.Compiler;

import org.example.AST.*;
import org.example.Entiy.*;
import org.example.Exception.CompilationError;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Compiler.CompilerOperations.*;
import org.example.Translator.Lexer;
import org.example.Translator.Parser.ParserCode;

import java.util.*;
public class Compiler extends CompilerBase {
    private final BufferFunctions bufferFunctions = new BufferFunctions();
    private final Scope scope = new Scope(this);
    private StatementsNode statementsNode;
    private final CompilerValue compilerValueNode = new CompilerValue();
    private final CompilerCreateFunction compilerCreateFunction = new CompilerCreateFunction(bufferFunctions);
    private final CompilerCallVariable compilerCallVariable = new CompilerCallVariable(scope);
    private final CompilerCreateVariable compilerCreateVariable = new CompilerCreateVariable(scope);
    private final CompilerArithmeticOperation compilerArithmeticOperation = new CompilerArithmeticOperation(this);

    public Compiler(String stringCode) {
        Lexer lexer = new Lexer(stringCode);
        List<Token> tokenList = lexer.lexAnalysis();
        Code code = new Code(tokenList);
        ParserCode parserCode = new ParserCode(code,bufferFunctions);
        this.statementsNode = (StatementsNode) parserCode.parse(null);
    }

    public Compiler() {
    }

    public void compile() {
        compile(statementsNode);
    }

    public Object compile(ExpressionNode node) {
        CompilerLogicOperation compileLogicBranch = new CompilerLogicOperation(scope,this);
        if (node instanceof ValueNode) return compilerValueNode.compile(node);

        if (node instanceof UnarOperationNode) {
            return compileCallFunction((UnarOperationNode) node);
        }

        if(node instanceof FuncNode){
            return compilerCreateFunction.compile(node);
        }

        if (node instanceof LogicNode){
            return compileLogicBranch.compile(node);
        }

        if (node instanceof BindOperationNode){
            Token operator = node.getToken();
            TokenType typeOperator = operator.type();
            if (typeOperator==TokenType.ASSIGN){
                return compilerCreateVariable.compile(node);
            }
            if (typeOperator==TokenType.COMPRESSION_OPERATOR || typeOperator==TokenType.LOGIC_OPERATOR) {
                return compileLogicBranch.isTrueLogicExpression((BindOperationNode) node);
            }
            else return compilerArithmeticOperation.compile(node);
        }

        if (node instanceof VariableNode){
            return compilerCallVariable.compile(node);
        }

        if (node instanceof StatementsNode){
            compileCodFromRootNode(node);
            return null;
        }
        throw new CompilationError("был передан не существующий тип команды",node.getPosition());
    }

    private Object compileCallFunction(UnarOperationNode node) {
        CompilerCallFunction compilerCallFunction = new CompilerCallFunction(bufferFunctions,scope,this);
        return compilerCallFunction.compile(node);
    }

    private void compileCodFromRootNode(ExpressionNode node) {
        StatementsNode statementsNode = (StatementsNode) node;
        List<ExpressionNode> codeNodes = statementsNode.getNodes();
        for (ExpressionNode codeNode : codeNodes) {
            compile(codeNode);
        }
    }
}
