package com.innovations.aguilar.pocketstrats.parser;

import java.util.Objects;

public class TipNode {
    public String Message;
    public int Precedence;
    public InfoNode INode;

    public TipNode() {
    }

    public TipNode(String message, int precedence, InfoNode INode) {
        Message = message;
        Precedence = precedence;
        this.INode = INode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipNode tipNode = (TipNode) o;
        return Precedence == tipNode.Precedence &&
                Objects.equals(Message, tipNode.Message) &&
                Objects.equals(INode, tipNode.INode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Message, Precedence, INode);
    }
}
