package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

public class ParsedNode {
    String nodeType;
    List<String> nodeContents;

    public ParsedNode(String nodeType, List<String> nodeContents) {
        this.nodeType = nodeType;
        this.nodeContents = nodeContents;
    }

    public ParsedNode(String nodeType, String nodeContents) {
        this.nodeType = nodeType;
        this.nodeContents = Lists.newArrayList(nodeContents);
    }

    @Override
    public String toString() {
        return String.format("{'%s':'%s'}", nodeType, nodeContents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsedNode that = (ParsedNode) o;
        return Objects.equals(nodeType, that.nodeType) &&
                Objects.equals(nodeContents, that.nodeContents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeType, nodeContents);
    }
}
