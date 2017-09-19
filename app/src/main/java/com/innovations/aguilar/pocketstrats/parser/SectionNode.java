package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

public class SectionNode extends TipNode {
    public List<TipNode> TipNodes = Lists.newArrayList();
    public List<PickNode> PickNodes = Lists.newArrayList();
    public SectionNode() {
    }
    public SectionNode(String message, int precedence, InfoNode INode, List<TipNode> tipNodes, List<PickNode> pickNodes) {
        super(message, precedence, INode);
        TipNodes = tipNodes;
        PickNodes = pickNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SectionNode that = (SectionNode) o;
        return Objects.equals(TipNodes, that.TipNodes) &&
                Objects.equals(PickNodes, that.PickNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), TipNodes, PickNodes);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SectionNode{");
        sb.append("TipNodes=").append(TipNodes);
        sb.append(", PickNodes=").append(PickNodes);
        sb.append(", TipNode=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
