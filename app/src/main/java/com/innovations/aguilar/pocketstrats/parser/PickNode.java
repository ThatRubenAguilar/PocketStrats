package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

public class PickNode extends TipNode {
    public List<TipNode> TipNodes = Lists.newArrayList();
    public PickNode() {
    }
    public PickNode(String message, int precedence, InfoNode INode, List<TipNode> tipNodes) {
        super(message, precedence, INode);
        TipNodes = tipNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PickNode pickNode = (PickNode) o;
        return Objects.equals(TipNodes, pickNode.TipNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), TipNodes);
    }
}
