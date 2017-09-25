package com.innovations.aguilar.pocketstrats.ui;

import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class MapSubjectHeader extends ExpandableGroup<MapSubjectChildInfo> {
    private final MapSubjectDTO subject;

    public MapSubjectHeader(MapSubjectDTO subject, List<MapSubjectChildInfo> items) {
        super(subject.getMapSubjectDescription(), items);
        this.subject = subject;
    }

    public MapSubjectDTO getSubject() {
        return subject;
    }
}

