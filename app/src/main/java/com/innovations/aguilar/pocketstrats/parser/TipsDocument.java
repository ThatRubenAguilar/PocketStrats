package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.Sets;
import com.innovations.aguilar.pocketstrats.logging.LoggerSupplier;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;

import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TipsDocument {
    protected Supplier<Logger> log = Suppliers.memoize(new LoggerSupplier(this.getClass()));

    List<SubjectNode> Subjects;

    int tipPrecedenceAccum = 1;

    public TipsDocument(List<TextNode> rawNodes) {
        Subjects = parseRawNodes(rawNodes);
    }

    final Set<String> infoLevelTokens = Sets.newHashSet(Tokens.Map, Tokens.Side, Tokens.Tags, Tokens.Category, Tokens.Type, Tokens.MapSegment);
    final Set<String> subjectLevelTokens = Sets.newHashSet(Tokens.Subject, Tokens.Strategy);
    final Set<String> sectionLevelTokens = Sets.newHashSet(Tokens.Section);
    final Set<String> pickLevelTokens = Sets.newHashSet(Tokens.Pick);
    final Set<String> tipLevelTokens = Sets.newHashSet(Tokens.Tip);
    List<SubjectNode> parseRawNodes(List<TextNode> allNodes) {
        List<SubjectNode> subjects = Lists.newArrayList();
        InfoNode currentInfo = new InfoNode();
        PeekingIterator<TextNode> rawNodes = Iterators.peekingIterator(allNodes.iterator());

        while (rawNodes.hasNext()) {
            TextNode currentNode = rawNodes.peek();
            if (infoLevelTokens.contains(currentNode.nodeType))
                currentInfo = HandleInfoNode(rawNodes, currentInfo);
            else if (subjectLevelTokens.contains(currentNode.nodeType)) {
                SubjectNode sNode = parseSubjectNode(rawNodes, currentInfo);
                sNode.Precedence = subjects.size() + 1;
                subjects.add(sNode);
            }
            else {
                log.get().warn(String.format("Unprocessed node in rawNodes '%s'", currentNode));
                rawNodes.next();
            }
        }
        return Collections.unmodifiableList(subjects);

    }

    SubjectNode parseSubjectNode(PeekingIterator<TextNode> rawNodes, InfoNode currentInfo) {
        TextNode currentNode = rawNodes.next();
        SubjectNode subjNode = new SubjectNode();
        subjNode.INode = currentInfo.Copy();
        subjNode.Message = currentNode.nodeContents.get(0);
        while(rawNodes.hasNext()) {
            currentNode = rawNodes.peek();
            if (infoLevelTokens.contains(currentNode.nodeType))
                currentInfo = HandleInfoNode(rawNodes, currentInfo);
            else if (sectionLevelTokens.contains(currentNode.nodeType)) {
                SectionNode sectNode = parseSectionNode(rawNodes, currentInfo);
                sectNode.Precedence = subjNode.SectionNodes.size() + 1;
                subjNode.SectionNodes.add(sectNode);
            }
            else if (Tokens.AllTokens.contains(currentNode.nodeType))
                return subjNode;
            else {
                log.get().warn(String.format("Unprocessed node in subjectNodes '%s'", currentNode));
                rawNodes.next();
            }
        }
        return subjNode;
    }

    SectionNode parseSectionNode(PeekingIterator<TextNode> rawNodes, InfoNode currentInfo) {
        TextNode currentNode = rawNodes.next();
        SectionNode sectNode = new SectionNode();
        sectNode.INode = currentInfo.Copy();
        sectNode.Message = currentNode.nodeContents.get(0);
        while(rawNodes.hasNext()) {
            currentNode = rawNodes.peek();
            if (infoLevelTokens.contains(currentNode.nodeType))
                currentInfo = HandleInfoNode(rawNodes, currentInfo);
            else if (tipLevelTokens.contains(currentNode.nodeType)) {
                TipNode tipNode = parseTipNode(rawNodes, currentInfo);
                tipNode.Precedence = sectNode.TipNodes.size() + sectNode.PickNodes.size() + 1;
                sectNode.TipNodes.add(tipNode);
            }
            else if (pickLevelTokens.contains(currentNode.nodeType)) {
                PickNode pickNode = parsePickNode(rawNodes, currentInfo);
                pickNode.Precedence = sectNode.TipNodes.size() + sectNode.PickNodes.size() + 1;
                sectNode.PickNodes.add(pickNode);
            }
            else if (Tokens.AllTokens.contains(currentNode.nodeType))
                return sectNode;
            else {
                log.get().warn(String.format("Unprocessed node in sectionNodes '%s'", currentNode));
                rawNodes.next();
            }
        }
        return sectNode;
    }

    TipNode parseTipNode(PeekingIterator<TextNode> rawNodes, InfoNode currentInfo) {
        TextNode currentNode = rawNodes.next();
        TipNode tipNode = new TipNode();
        tipNode.INode = currentInfo.Copy();
        tipNode.Message = currentNode.nodeContents.get(0);
        return tipNode;
    }
    PickNode parsePickNode(PeekingIterator<TextNode> rawNodes, InfoNode currentInfo) {
        TextNode currentNode = rawNodes.next();
        PickNode pickNode = new PickNode();
        pickNode.INode = currentInfo.Copy();
        pickNode.Message = currentNode.nodeContents.get(0);
        while(rawNodes.hasNext()) {
            currentNode = rawNodes.peek();
            if (infoLevelTokens.contains(currentNode.nodeType))
                currentInfo = HandleInfoNode(rawNodes, currentInfo);
            else if (tipLevelTokens.contains(currentNode.nodeType)) {
                TipNode tipNode = parseTipNode(rawNodes, currentInfo);
                tipNode.Precedence = pickNode.TipNodes.size() + 1;
                pickNode.TipNodes.add(tipNode);
            }
            else if (Tokens.AllTokens.contains(currentNode.nodeType))
                return pickNode;
            else {
                log.get().warn(String.format("Unprocessed node in pickNodes '%s'", currentNode));
                rawNodes.next();
            }
        }
        return pickNode;
    }

    private InfoNode HandleInfoNode(PeekingIterator<TextNode> rawNodes, InfoNode currentInfo) {
        TextNode node = rawNodes.next();
        switch(node.nodeType) {
            case Tokens.Map:
                currentInfo.MapName = node.nodeContents.get(0);
                break;
            case Tokens.Tags:
                break;
            case Tokens.Category:
                currentInfo.MapTypes.clear();
                for (String mapType :
                        node.nodeContents) {
                    currentInfo.MapTypes.add(MapType.valueOf(mapType));
                }
                break;
            case Tokens.Type:
                break;
            case Tokens.Side:
                currentInfo.Sides.clear();
                for (String spawnSide :
                        node.nodeContents) {
                    currentInfo.Sides.add(SpawnSide.valueOf(spawnSide));
                }
                break;
            case Tokens.MapSegment:
                currentInfo.SegmentName = node.nodeContents.get(0);
                break;
        }
        return currentInfo;
    }

    public List<SubjectNode> getSubjects() {
        return Subjects;
    }
}
