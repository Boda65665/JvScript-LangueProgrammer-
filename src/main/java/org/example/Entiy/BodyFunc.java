package org.example.Entiy;

import org.example.AST.ExpressionNode;
import org.example.AST.StatementsNode;

public record BodyFunc(StatementsNode code, ExpressionNode returnedData) {}
