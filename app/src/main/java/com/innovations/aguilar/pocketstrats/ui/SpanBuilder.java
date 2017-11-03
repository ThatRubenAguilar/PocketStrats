package com.innovations.aguilar.pocketstrats.ui;

import android.text.Spannable;
import android.text.SpannableStringBuilder;

/**
 * Created by Ruben on 11/1/2017.
 */
public class SpanBuilder {

    private boolean paragraphNewlines;
    private boolean firstSpanData;

    public interface SpanConfigurator<TData> {
        // Returns the span delta incurred by spanData (Generally the length of the string)
        int configure(SpannableStringBuilder builder, TData spanData, int runningOffset);
    }

    private int runningTextOffset;
    private SpannableStringBuilder runningBuilder;

    public SpanBuilder() {
        this(false);
    }

    public SpanBuilder(boolean paragraphNewlines) {
        this.paragraphNewlines = paragraphNewlines;
        reset();
    }

    public void reset() {
        runningBuilder = new SpannableStringBuilder();
        runningTextOffset = 0;
        firstSpanData = true;
    }

    public Spannable getSpan() {
        return runningBuilder;
    }

    public <TData> void appendSpanData(TData spanData, SpanConfigurator<TData> spanConfigurator) {
        if (paragraphNewlines) {
            if (firstSpanData)
                firstSpanData = false;
            else {
                runningBuilder.append("\n");
                runningTextOffset += 1;
            }
        }
        runningTextOffset += spanConfigurator.configure(runningBuilder, spanData, runningTextOffset);
    }

    public static final SpanConfigurator<String> PlainConfigurator = new PlainSpanConfigurator();

    public static class PlainSpanConfigurator implements SpanConfigurator<String> {
        @Override
        public int configure(SpannableStringBuilder builder, String spanData, int runningOffset) {
            builder.append(spanData);
            return spanData.length();
        }
    }
}
