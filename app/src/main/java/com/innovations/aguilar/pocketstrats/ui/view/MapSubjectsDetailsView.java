package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.LeadingMarginSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.CustomTypefaceSpan;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;
import com.innovations.aguilar.pocketstrats.ui.SpanBuilder;
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

public class MapSubjectsDetailsView extends LinearLayout {
    protected static Logger log = LoggerFactory.getLogger(MapSubjectsDetailsView.class);

    Supplier<MainPaneContainer> mainContainer;

    LinearLayout detailsLayout;

    Supplier<List<View>> attackView;
    Supplier<List<View>> defendView;
    Supplier<MapSubjectTipDataHolder> attackSupplier;
    Supplier<MapSubjectTipDataHolder> defendSupplier;

    SpanBuilder.SpanConfigurator<MapTipDTO> tipSpanConfigurator = new TipSpanConfigurator();
    SpanBuilder.SpanConfigurator<MapTipDTO> sectionSpanConfigurator = new SectionSpanConfigurator();

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
        detailsLayout = (LinearLayout)findViewById(R.id.layout_subject_details_scrollable);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainContainer = Suppliers.memoize(new Supplier<MainPaneContainer>() {
            @Override
            public MainPaneContainer get() {
                return (MainPaneContainer) ((MainActivity) getContext()).findViewById(R.id.layout_main_container);
            }
        });
    }

    private TextView createAndConfigureTextView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        TextView text = (TextView) inflater.inflate(R.layout.map_subject_details_text, detailsLayout, false);
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


    public void loadSubjectDetailsForSide(SpawnSide side) {
        if (side == SpawnSide.Attack) {
            detailsLayout.removeAllViews();
            for (View v :
                    attackView.get()) {
                detailsLayout.addView(v);
            }
        } else if (side == SpawnSide.Defend) {
            detailsLayout.removeAllViews();
            for (View v :
                    defendView.get()) {
                detailsLayout.addView(v);
            }
        } else {
            log.warn("loadSubjectDetailsForSide with unknown SpawnSide");
        }
    }

    // TODO: AdapterMap that manages type -> adapater relations ?
    public void loadSubjectDetails(MapSubjectDTO subject) {
        if (subject.getSpawnSideId() == SpawnSide.Attack) {
            attackSupplier = createSupplierForSubject(subject);
            defendSupplier = createSupplierForAssociatedSubject(subject);
            attackView = createAndConfigureViewsForSide(attackSupplier);
            defendView = createAndConfigureViewsForSide(defendSupplier);
        } else if (subject.getSpawnSideId() == SpawnSide.Defend) {
            defendSupplier = createSupplierForSubject(subject);
            attackSupplier = createSupplierForAssociatedSubject(subject);
            attackView = createAndConfigureViewsForSide(attackSupplier);
            defendView = createAndConfigureViewsForSide(defendSupplier);
        } else {
            log.warn("SpawnSide unknown in loadSubjectDetails");
        }

        loadSubjectDetailsForSide(subject.getSpawnSideId());
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
                MapDatabaseOpenHelper openHelper = new MapDatabaseOpenHelper(getContext());
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


    class TipSpanConfigurator implements SpanBuilder.SpanConfigurator<MapTipDTO> {

        @Override
        public int configure(SpannableStringBuilder builder, MapTipDTO spanData, int runningOffset) {
            String tipDetail = spanData.getMapTipDescription();
            Context context = getContext();

            int spanDeltaOffset = tipDetail.length()+2;

            builder.append("• ");
            builder.append(tipDetail);
            builder.setSpan(new AbsoluteSizeSpan(
                            context.getResources().getDimensionPixelSize(R.dimen.paragraph_text_size_medium)),
                    runningOffset, runningOffset+spanDeltaOffset, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            builder.setSpan(new CustomTypefaceSpan(
                            CustomTypeFaces.Futura(context.getAssets())),
                    runningOffset, runningOffset+spanDeltaOffset, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

            int leading = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_tip);
            int offset = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_offset);
            LeadingMarginSpan adjustSpan = new LeadingMarginSpan.Standard(leading, leading + offset);
            builder.setSpan(adjustSpan, runningOffset, runningOffset+spanDeltaOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spanDeltaOffset;
        }
    }
    class SectionSpanConfigurator implements SpanBuilder.SpanConfigurator<MapTipDTO> {

        @Override
        public int configure(SpannableStringBuilder builder, MapTipDTO spanData, int runningOffset) {
            String tipDetail = spanData.getMapTipDescription();
            Context context = getContext();

            int spanDeltaOffset = tipDetail.length()+2;

            builder.append("- ");
            builder.append(tipDetail);

            builder.setSpan(new AbsoluteSizeSpan(
                            context.getResources().getDimensionPixelSize(R.dimen.paragraph_text_size_medium)),
                    runningOffset, runningOffset+spanDeltaOffset, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            builder.setSpan(new CustomTypefaceSpan(
                            CustomTypeFaces.Futura(context.getAssets())),
                    runningOffset, runningOffset+spanDeltaOffset, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

            int leading = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_section);
            int offset = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_offset);
            LeadingMarginSpan adjustSpan = new LeadingMarginSpan.Standard(leading, leading + offset);
            builder.setSpan(adjustSpan, runningOffset, runningOffset+spanDeltaOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spanDeltaOffset;
        }
    }
}
