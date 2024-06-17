package org.example.Translator;

import org.example.AST.FuncNode;
import java.util.HashMap;

public class BufferFunctions {
    private final HashMap<String,String[]> namesArgumentFunction = new HashMap<>();
    private final HashMap<String, FuncNode> functions = new HashMap<>();
    
    private void initBuffer() {
        namesArgumentFunction.put("input", new String[]{"typeValueReceived"});
        namesArgumentFunction.put("log", new String[]{"valueLog"});
        namesArgumentFunction.put("logLn", new String[]{"valueLog"});
    }

    public BufferFunctions() {
        initBuffer();
    }

    public String[] getNamesArgumentFunction(String nameFunc) {
        return namesArgumentFunction.get(nameFunc);
    }

    public void putNamesArgumentsToBuffer(String nameFunc,String[] namesArg){
        namesArgumentFunction.put(nameFunc,namesArg);
    }

    public FuncNode getFunction(String nameFunc) {
        return this.functions.get(nameFunc);
    }

    public void putFunction(String nameFunc,FuncNode funcNode){
        this.functions.put(nameFunc,funcNode);
    }
}
