package com.innovations.aguilar.pocketstrats.ui.view;

import android.text.SpannableStringBuilder;

import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDTO;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ruben on 11/10/2017.
 */
public abstract class ChunkedTipSpanConfigurator {
    protected static final Pattern mapCalloutRegex = Pattern.compile("\\[[^]]*\\]");

    protected static class TipContainer {
        public boolean isLink() {
            return link;
        }

        private final boolean link;

        public String getMsg() {
            return msg;
        }

        private final String msg;

        public TipContainer(boolean link, String msg) {
            this.link = link;
            this.msg = msg;
        }


        public static TipContainer makeTextContainer(String tipMsg, int start, int end) {
            return new TipContainer(false, tipMsg.substring(start, end));
        }

        public static TipContainer makeLinkContainer(String tipMsg, int start, int end) {
            return new TipContainer(true, tipMsg.substring(start, end));
        }
    }

    protected int configureChunkedTip(SpannableStringBuilder builder, String prefix, String tipDetail, int runningOffset) {
        int spanDeltaOffset = 0;

        builder.append(prefix);

        int startSegmentOffset = runningOffset+spanDeltaOffset;
        int endSegmentOffset = runningOffset+spanDeltaOffset+prefix.length();
        styleTextSpan(builder, startSegmentOffset, endSegmentOffset);
        spanDeltaOffset += prefix.length();

        List<TipContainer> tipContainers = chunkTipData(tipDetail);

        for (TipContainer tipContainer :
                tipContainers) {
            builder.append(tipContainer.getMsg());
            startSegmentOffset = runningOffset+spanDeltaOffset;
            endSegmentOffset = runningOffset+spanDeltaOffset+tipContainer.getMsg().length();
            if (tipContainer.isLink()) {
                styleLinkSpan(builder, startSegmentOffset, endSegmentOffset, tipContainer.getMsg());
            } else {
                styleTextSpan(builder, startSegmentOffset, endSegmentOffset);
            }
            spanDeltaOffset += tipContainer.getMsg().length();
        }
        return spanDeltaOffset;
    }

    protected abstract void styleLinkSpan(SpannableStringBuilder builder, int startSegmentOffset, int endSegmentOffset, final String mapCalloutLink);
    protected abstract void styleTextSpan(SpannableStringBuilder builder, int startSegmentOffset, int endSegmentOffset);

    protected static List<TipContainer> chunkTipData(String tipMsg) {
        List<TipContainer> containers = Lists.newArrayList();
        Matcher matches = mapCalloutRegex.matcher(tipMsg);
        int start = 0;
        while (matches.find()) {
            int groupStart = matches.start();
            int groupEnd = matches.end();

            if (groupStart != start) {
                containers.add(TipContainer.makeTextContainer(tipMsg, start, groupStart));
            }
            containers.add(TipContainer.makeLinkContainer(tipMsg, groupStart, groupEnd));

            start = groupEnd;
            if (start > tipMsg.length())
                start = tipMsg.length();
        }

        int end = tipMsg.length();
        if (start != end) {
            containers.add(TipContainer.makeTextContainer(tipMsg, start, end));
        }

        return containers;
    }
}
