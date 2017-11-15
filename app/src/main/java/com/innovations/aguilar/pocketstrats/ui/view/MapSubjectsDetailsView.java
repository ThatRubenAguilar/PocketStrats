package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.LeadingMarginSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.HeroDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.sql.query.MapDatabaseOpenHelper;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.ui.Container;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.CustomTypefaceSpan;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.SpanBuilder;
import com.innovations.aguilar.pocketstrats.ui.SwipeAnimation;
import com.innovations.aguilar.pocketstrats.ui.dataholder.DataHolderAccessor;
import com.innovations.aguilar.pocketstrats.ui.dataholder.MapHeroPickTipDataHolder;
import com.innovations.aguilar.pocketstrats.ui.dataholder.MapSectionTipDataHolder;
import com.innovations.aguilar.pocketstrats.ui.dataholder.MapSubjectTipDataHolder;
import com.innovations.aguilar.pocketstrats.ui.dataholder.MapTipDataHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MapSubjectsDetailsView extends FrameLayout {
    protected static Logger log = LoggerFactory.getLogger(MapSubjectsDetailsView.class);

    Supplier<Container> mainContainer;

    LinearLayout detailsLayout;
    LinearLayout transitionDetailsLayout;
    VerticalScrollView detailsScrollableView;

    Supplier<List<View>> attackView;
    Supplier<List<View>> defendView;
    Supplier<MapSubjectTipDataHolder> attackSupplier;
    Supplier<MapSubjectTipDataHolder> defendSupplier;

    SpanBuilder.SpanConfigurator<MapTipDTO> tipSpanConfigurator = new TipSpanConfigurator(getContext());
    SpanBuilder.SpanConfigurator<MapTipDTO> sectionSpanConfigurator = new SectionSpanConfigurator(getContext());

    // TODO: Text for tips becomes scrunched in smaller viewports, consider changing text size
    public MapSubjectsDetailsView(Context context) {
        super(context);
        inflateLayout();
    }

    public MapSubjectsDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayout();
    }

    public MapSubjectsDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout();
    }

    private void inflateLayout() {
        inflate(getContext(), R.layout.map_subject_details, this);
        detailsLayout = (LinearLayout)findViewById(R.id.layout_subject_details_tips);
        detailsScrollableView = (VerticalScrollView) findViewById(R.id.layout_subject_details_scrollable);
        transitionDetailsLayout = (LinearLayout)findViewById(R.id.layout_subject_details_tips_transition);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainContainer = MainActivity.generateContainerRef(this);
    }

    private TextView createAndConfigureTextView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        TextView text = (TextView) inflater.inflate(R.layout.map_subject_details_text, detailsLayout, false);
        text.setMovementMethod(LinkMovementMethod.getInstance());
        return text;
    }

    private HeroDetailsGrid createAndConfigureHeroDetails() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        HeroDetailsGrid grid = (HeroDetailsGrid) inflater.inflate(R.layout.map_subject_details_hero_grid, detailsLayout, false);
        return grid;
    }

    private Supplier<List<View>> createAndConfigureViewsForSide(final Supplier<MapSubjectTipDataHolder> subjectSupplier) {
        Supplier<List<View>> viewListSupplier = Suppliers.memoize(new Supplier<List<View>>() {
            @Override
            public List<View> get() {
                List<View> views = Lists.newArrayList();
                TextView runningTextView = createAndConfigureTextView();
                SpanBuilder builder = new SpanBuilder(true);

                for (MapSectionTipDataHolder section:
                     subjectSupplier.get().getMapSectionTips()) {
                    if (runningTextView == null) {
                        runningTextView = createAndConfigureTextView();
                    }

                    builder.appendSpanData(section.getSectionTip(), sectionSpanConfigurator);
                    for (MapTipDataHolder tip :
                            section.getMapTips()) {
                        builder.appendSpanData(tip.getMapTip(), tipSpanConfigurator);
                    }

                    if (!section.getMapHeroPickTips().isEmpty()) {
                        runningTextView.setText(builder.getSpan());
                        views.add(runningTextView);
                        runningTextView = null;
                        builder.reset();

                        HeroDetailsGrid heroDetails = createAndConfigureHeroDetails();

                        Map<HeroDataDTO, List<MapHeroPickTipDTO>> heroToTipMap = buildHeroToTipMap(section.getMapHeroPickTips());
                        heroDetails.setHeroPickTips(heroToTipMap);

                        views.add(heroDetails);
                    }
                }

                if (runningTextView != null) {
                    runningTextView.setText(builder.getSpan());
                    views.add(runningTextView);
                }

                return Collections.unmodifiableList(views);
            }
        });

        return viewListSupplier;
    }

    private Map<HeroDataDTO, List<MapHeroPickTipDTO>> buildHeroToTipMap(List<MapHeroPickTipDataHolder> mapHeroPickTips) {
        Map<HeroDataDTO, List<MapHeroPickTipDTO>> heroToTipMap = Maps.newHashMap();
        for (MapHeroPickTipDataHolder heroPickTipDataHolder :
                mapHeroPickTips) {
            HeroDataDTO heroData = heroPickTipDataHolder.getHeroData();
            if (!heroToTipMap.containsKey(heroData))
                heroToTipMap.put(heroData, Lists.<MapHeroPickTipDTO>newArrayList());

            heroToTipMap.get(heroData).add(heroPickTipDataHolder.getMapHeroPickTip());
        }

        return heroToTipMap;
    }


    private void clearAndAddViewsToLayout(LinearLayout destLayout, Iterable<View> views) {
        destLayout.removeAllViews();
        for (View v :
                views) {
            destLayout.addView(v);
        }
    }

    private void clearAndAddViewsToLayout(LinearLayout destLayout, View ... views) {
        destLayout.removeAllViews();
        for (View v :
                views) {
            destLayout.addView(v);
        }
    }

    private void loadSubjectDetailsForSide(LinearLayout destLayout, SpawnSide side) {
        if (side == SpawnSide.Attack) {
            clearAndAddViewsToLayout(destLayout, attackView.get());
        } else if (side == SpawnSide.Defend) {
            clearAndAddViewsToLayout(destLayout, defendView.get());
        } else {
            log.warn("loadSubjectDetailsForSide with unknown SpawnSide");
        }
    }
    public void loadSubjectDetailsForSide(SpawnSide side) {
        loadSubjectDetailsForSide(detailsLayout, side);
    }

    // TODO: AdapterMap that manages type -> adapater relations ?
    public void loadSubjectDetails(MapSubjectDTO subject) {
        loadSubjectDetails(subject, null);
    }
    public void loadSubjectDetails(MapSubjectDTO subject, SwipeAnimation swipeTransition) {
        if (subject.getSpawnSideId() == SpawnSide.Attack) {
            Supplier<List<View>> oldViews = attackView;
            attackSupplier = createSupplierForSubject(subject);
            defendSupplier = createSupplierForAssociatedSubject(subject);
            attackView = createAndConfigureViewsForSide(attackSupplier);
            defendView = createAndConfigureViewsForSide(defendSupplier);
            loadSubjectDetailsForSide(subject.getSpawnSideId());
            if (swipeTransition != null && oldViews != null)
                animateSwipeTransition(oldViews.get(), swipeTransition);
        } else if (subject.getSpawnSideId() == SpawnSide.Defend) {
            Supplier<List<View>> oldViews = defendView;
            defendSupplier = createSupplierForSubject(subject);
            attackSupplier = createSupplierForAssociatedSubject(subject);
            attackView = createAndConfigureViewsForSide(attackSupplier);
            defendView = createAndConfigureViewsForSide(defendSupplier);
            loadSubjectDetailsForSide(subject.getSpawnSideId());
            if (swipeTransition != null && oldViews != null)
                animateSwipeTransition(oldViews.get(), swipeTransition);
        } else {
            log.warn("SpawnSide unknown in loadSubjectDetails");
        }

    }

    private void animateSwipeTransition(List<View> oldViews, SwipeAnimation swipeAnimation) {
        if (swipeAnimation != null) {
            clearAndAddViewsToLayout(transitionDetailsLayout, oldViews);
            swipeAnimation.swapTransition(detailsScrollableView, transitionDetailsLayout);
        }
        else {
            log.warn("Requested swipe transition without in and out swipe animations.");
        }
    }

    private Supplier<MapSubjectTipDataHolder> createSupplierForSubject(final MapSubjectDTO subject, boolean useAssociatedSubject) {
        if (!useAssociatedSubject)
            return createSupplierForSubject(subject);
        return createSupplierForAssociatedSubject(subject);
    }
    private Supplier<MapSubjectTipDataHolder> createSupplierForSubject(final MapSubjectDTO subject) {
        return Suppliers.memoize(new Supplier<MapSubjectTipDataHolder>() {
            @Override
            public MapSubjectTipDataHolder get() {
                MapSubjectTipDataHolder subjectDataHolder = new DataHolderAccessor(getContext()).queryDataHolderFromSubject(subject);

                return subjectDataHolder;
            }
        });
    }

    private Supplier<MapSubjectTipDataHolder> createSupplierForAssociatedSubject(final MapSubjectDTO subject) {
        return Suppliers.memoize(new Supplier<MapSubjectTipDataHolder>() {
            @Override
            public MapSubjectTipDataHolder get() {
                MapDatabaseOpenHelper openHelper = MapDatabaseOpenHelper.getHelper(getContext());
                try (SqlDataAccessor accessor = new SqlDataAccessor(openHelper.getReadableDatabase())) {
                    List<MapSubjectDTO> associatedSubjects = accessor.mapSubjectAccessor()
                            .GetMapSubjectsByAssociation(subject.getMapSubjectId());
                    MapSubjectDTO differentAssociatedSubject = null;
                    for (MapSubjectDTO associatedSubject :
                            associatedSubjects) {
                        if (associatedSubject.getSpawnSideId() != subject.getSpawnSideId()) {
                            differentAssociatedSubject = associatedSubject;
                            break;
                        }
                    }

                    MapSubjectTipDataHolder subjectDataHolder = new DataHolderAccessor(accessor)
                            .queryDataHolderFromSubject(differentAssociatedSubject);

                    return subjectDataHolder;
                }
            }
        });
    }


    static class TipSpanConfigurator extends ChunkedTipSpanConfigurator implements SpanBuilder.SpanConfigurator<MapTipDTO> {

        static final String Prefix = "• ";
        private Context context;

        public TipSpanConfigurator(Context context) {
            this.context = context;
        }

        @Override
        public int configure(SpannableStringBuilder builder, MapTipDTO spanData, int runningOffset) {
            String tipDetail = spanData.getMapTipDescription();

            return configureChunkedTip(builder, Prefix, tipDetail, runningOffset);
        }


        @Override
        protected void styleLinkSpan(SpannableStringBuilder builder, int startSegmentOffset, int endSegmentOffset, final String mapCalloutLink) {
            ClickableSpan linkSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    log.debug("Clicked {}", mapCalloutLink);
                }
            };
            builder.setSpan(linkSpan, startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styleTextSpan(builder, startSegmentOffset, endSegmentOffset);
        }
        @Override
        protected void styleTextSpan(SpannableStringBuilder builder, int startSegmentOffset, int endSegmentOffset) {

            builder.setSpan(new AbsoluteSizeSpan(
                            context.getResources().getDimensionPixelSize(R.dimen.paragraph_text_size_medium)),
                    startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new CustomTypefaceSpan(
                            CustomTypeFaces.Futura(context.getAssets())),
                    startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            int leading = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_tip);
            int offset = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_offset);
            LeadingMarginSpan adjustSpan = new LeadingMarginSpan.Standard(leading, leading + offset);
            builder.setSpan(adjustSpan, startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }

    static class SectionSpanConfigurator extends ChunkedTipSpanConfigurator implements SpanBuilder.SpanConfigurator<MapTipDTO> {

        static final String Prefix = "- ";
        private Context context;

        public SectionSpanConfigurator(Context context) {
            this.context = context;
        }

        @Override
        public int configure(SpannableStringBuilder builder, MapTipDTO spanData, int runningOffset) {
            String tipDetail = spanData.getMapTipDescription();

            return configureChunkedTip(builder, Prefix, tipDetail, runningOffset);
        }

        @Override
        protected void styleLinkSpan(SpannableStringBuilder builder, int startSegmentOffset, int endSegmentOffset, final String mapCalloutLink) {
            ClickableSpan linkSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    log.debug("Clicked {}", mapCalloutLink);
                }
            };
            builder.setSpan(linkSpan, startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styleTextSpan(builder, startSegmentOffset, endSegmentOffset);
        }

        @Override
        protected void styleTextSpan(SpannableStringBuilder builder, int startSegmentOffset, int endSegmentOffset) {
            builder.setSpan(new AbsoluteSizeSpan(
                            context.getResources().getDimensionPixelSize(R.dimen.paragraph_text_size_medium)),
                    startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new CustomTypefaceSpan(
                            CustomTypeFaces.Futura(context.getAssets())),
                    startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            int leading = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_section);
            int offset = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_offset);
            LeadingMarginSpan adjustSpan = new LeadingMarginSpan.Standard(leading, leading + offset);
            builder.setSpan(adjustSpan, startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
