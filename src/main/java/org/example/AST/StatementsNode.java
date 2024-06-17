package org.example.AST;

import org.example.Entiy.Position;

import java.util.ArrayList;
import java.util.List;

public class StatementsNode extends ExpressionNode {
    private final List<ExpressionNode> nodes = new ArrayList<>();

    public StatementsNode() {
        super(null);
    }
    //корневой узел,поэтому поизиция начальная
    public Position getPosition(int indexNode){
        return nodes.get(indexNode).getPosition();
    }

    public void addNode(ExpressionNode node){
        this.nodes.add(node);
    }

    public List<ExpressionNode> getNodes() {
        return nodes;
    }
}
