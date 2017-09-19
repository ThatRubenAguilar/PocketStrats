package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

public class SubjectNode extends TipNode {
    public List<SectionNode> SectionNodes = Lists.newArrayList();
    public SubjectNode() {
    }
    public SubjectNode(String message, int precedence, InfoNode INode, List<SectionNode> sectionNodes) {
        super(message, precedence, INode);
        SectionNodes = sectionNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubjectNode that = (SubjectNode) o;
        return Objects.equals(SectionNodes, that.SectionNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), SectionNodes);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SubjectNode{");
        sb.append("SectionNodes=").append(SectionNodes);
        sb.append(", TipNode=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
