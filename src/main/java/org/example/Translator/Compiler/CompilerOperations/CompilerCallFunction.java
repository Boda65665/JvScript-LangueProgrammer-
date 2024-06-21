package org.example.Translator.Compiler.CompilerOperations;

import org.example.AST.*;
import org.example.Entiy.*;
import org.example.Exception.CompilationError;
import org.example.Entiy.BufferFunctions;
import org.example.Translator.Compiler.Compiler;
import org.example.Translator.Compiler.CompilerBase;
import org.example.Entiy.Scope;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CompilerCallFunction extends CompilerBase {
    private final ArrayList<String> BUILD_FUNC_NAMES = new ArrayList<>(Arrays.asList("log", "logLn", "input"));
    private final BufferFunctions bufferFunctions;
    private final Scope scope;
    private final Compiler compiler;

    public CompilerCallFunction(BufferFunctions bufferFunctions, Scope scope, Compiler compiler) {
        this.bufferFunctions = bufferFunctions;
        this.scope = scope;
        this.compiler = compiler;
    }

    @Override
    public Object compile(ExpressionNode node) {
        Token tokenCallFunction = node.getToken();
        String nameFunc = tokenCallFunction.text();
        if(areBuildFunc(nameFunc)){
            return callBuildFunc((UnarOperationNode) node,nameFunc);
        }
        else {
            return callCustomFunc((UnarOperationNode) node,nameFunc);
        }
    }

    private boolean areBuildFunc(String nameFunc) {
        return BUILD_FUNC_NAMES.contains(nameFunc);
    }

    private Object callBuildFunc(UnarOperationNode node,String nameFunc) {
        if (nameFunc.equals("log") || nameFunc.equals("logLn")) {
            printValueNode(node);
            return null;
        }
        else {
            return input(node);
        }
    }

    private void printValueNode(UnarOperationNode node) {
        ArgumentNode argumentNode = (ArgumentNode) (node).getOperand();
        Object outputValue = compiler.compile(argumentNode.getArg("valueLog"));
        if(node.getToken().text().equals("logLn")) System.out.println(outputValue);
        else System.out.print(outputValue);
    }

    private Object input(UnarOperationNode inputNode) {
        ArgumentNode argumentNode = (ArgumentNode) (inputNode).getOperand();
        ValueNode typeNode = (ValueNode) argumentNode.getArg("typeValueReceived");
        Token typeToken = typeNode.getToken();
        String typeInputValue = typeToken.text();
        ValueType valueType = ValueType.getTypeFromString(typeInputValue);
        if (valueType==null) throw new CompilationError("Был передан не валидный тип",typeNode.getPosition());
        Scanner scanner = new Scanner(System.in);
        try {
            return switch (valueType) {
                case INT -> scanner.nextInt();
                case DOUBLE -> scanner.nextDouble();
                case BOOL -> scanner.nextBoolean();
                default -> scanner.nextLine();
            };
        }
        catch (InputMismatchException err){
            throw new CompilationError(String.format("ожидался тип %s",valueType),inputNode.getOperand().getPosition());
        }
    }

    private Object callCustomFunc(UnarOperationNode node,String nameFunc) {

        scope.setLocalVisible();
        FuncNode calledFunction = bufferFunctions.getFunction(nameFunc);
        validCallFunction(node,nameFunc);
        argumentFunctionToLocalVar(calledFunction,(ArgumentNode) node.getOperand());
        BodyFunc bodyFunc = calledFunction.getBodyFunc();
        compiler.compile(bodyFunc.code());
        Object returnValue = (calledFunction.getReturnType()==null)?null:compiler.compile(bodyFunc.returnedData());
        scope.setGlobalVisible();
        return returnValue;
    }

    private void validCallFunction(UnarOperationNode calledFunctionNode,String nameFunc) {
        FuncNode calledFunction = bufferFunctions.getFunction(nameFunc);
        validArgumentFunction(calledFunction,calledFunctionNode);
    }

    private void validArgumentFunction(FuncNode calledFunction,UnarOperationNode calledFunctionNode) {

        ArgumentExceptedNode argumentExceptedNode = calledFunction.getArgumentExceptedNode();
        ArgumentNode argumentNode = (ArgumentNode) calledFunctionNode.getOperand();
        String[] namesArgumentsFunction = bufferFunctions.getNamesArgumentFunction(calledFunction.getNameFunc());
        String nameFunc = calledFunction.getNameFunc();

        for (int i = 0; i < argumentExceptedNode.getSizeArg(); i++) {
            String nameArg = namesArgumentsFunction[i];
            ValueType exceptedTypeArg = argumentExceptedNode.getExceptedArg(nameArg);
            ExpressionNode arg = argumentNode.getArg(nameArg);
            TokenType argTokenType = arg.getTokenType();
            if (argTokenType != TokenType.NAME) {
            if (!ValueType.instance(argTokenType, exceptedTypeArg)) {
                    throw new CompilationError(String.format("в функции %s, аргумент %s имеет неверный тип", nameFunc, nameArg),
                            calledFunctionNode.getPosition());
                }
            } else {
                Token argToken = arg.getToken();
                String namePassedValue = argToken.text();
                ValueType argType;
                if (argumentNode.getArg(nameArg) instanceof VariableNode) {
                    argType = scope.getTypeVar(namePassedValue);
                } else {
                    argType = getTypeReturnFunction(namePassedValue);
                }

                if (argType == null || argType != exceptedTypeArg) {
                    throw new CompilationError(String.format("в функции %s, аргумент %s имеет неверный тип", nameFunc, nameArg),
                            calledFunctionNode.getPosition());
                }
            }
        }
    }

    public ValueType getTypeReturnFunction(String nameFunction) {
        FuncNode funcNode = bufferFunctions.getFunction(nameFunction);
        return (funcNode==null)?null:funcNode.getReturnType();
    }

    private void argumentFunctionToLocalVar(FuncNode calledFunction, ArgumentNode operand) {
        String[] namesArgs = bufferFunctions.getNamesArgumentFunction(calledFunction.getNameFunc());
        ExpressionNode[] passedArgs = operand.getAllArgs();
        ArgumentExceptedNode argumentExcepted = calledFunction.getArgumentExceptedNode();
        for (int i = 0; i <namesArgs.length; i++) {
            ValueType typeArg = argumentExcepted.getExceptedArg(namesArgs[i]);
            Object valueVar = compiler.compile(passedArgs[i]);
            scope.setLocalVar(namesArgs[i],new Variable(valueVar,typeArg));
        }
    }
}
