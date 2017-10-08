package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.sql.query.MapDatabaseOpenHelper;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;
import com.innovations.aguilar.pocketstrats.ui.TipText;
import com.innovations.aguilar.pocketstrats.ui.adapter.MapSubjectDetailsTipItemAdapter;

import java.util.List;

public class MapSubjectsDetailsView extends LinearLayout {

    Supplier<MainPaneContainer> mainContainer;

    RecyclerView detailsView;

    Supplier<MapSubjectDetailsTipItemAdapter> attackSupplier;
    Supplier<MapSubjectDetailsTipItemAdapter> defendSupplier;

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
        detailsView = (RecyclerView)findViewById(R.id.list_tips);
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

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setAutoMeasureEnabled(true);
        detailsView.setLayoutManager(manager);

    }

    public void loadSubjectDetailsForSide(SpawnSide side) {
        if (side == SpawnSide.Attack) {
            detailsView.setAdapter(attackSupplier.get());
        } else if (side == SpawnSide.Defend) {
            detailsView.setAdapter(defendSupplier.get());
        } else {
            Log.w(this.getClass().toString(), "loadSubjectDetailsForSide with unknown SpawnSide");
        }
    }

    public void loadSubjectDetails(MapSubjectDTO subject) {
        if (subject.getSpawnSideId() == SpawnSide.Attack) {
            attackSupplier = createSupplierForSubject(subject);
            defendSupplier = createSupplierForAssociatedSubject(subject);
        } else if (subject.getSpawnSideId() == SpawnSide.Defend) {
            defendSupplier = createSupplierForSubject(subject);
            attackSupplier = createSupplierForAssociatedSubject(subject);
        } else {
            Log.w(this.getClass().toString(), "SpawnSide unknown in loadSubjectDetails");
        }

        loadSubjectDetailsForSide(subject.getSpawnSideId());
    }

    private boolean isSection(MapTipDTO tip){
        return tip.getParentMapTipId() == null;
    }

    private Supplier<MapSubjectDetailsTipItemAdapter> createSupplierForSubject(final MapSubjectDTO subject) {
        return new Supplier<MapSubjectDetailsTipItemAdapter>() {
            @Override
            public MapSubjectDetailsTipItemAdapter get() {
                List<MapSpecificTipDTO> specificTips;
                List<MapTypeTipDTO> typeTips;
                MapDatabaseOpenHelper openHelper = new MapDatabaseOpenHelper(getContext());
                try (SqlDataAccessor accessor = new SqlDataAccessor(openHelper.getReadableDatabase())) {
                    specificTips = accessor.mapTipAccessor().GetMapSpecificTipsByMapSubject(subject.getMapSubjectId());
                    typeTips = accessor.mapTipAccessor().GetMapTypeTipsByMapSubject(subject.getMapSubjectId());
                }

                List<TipText> tips = Lists.newArrayList();
                for (MapSpecificTipDTO specificTip :
                        specificTips) {
                    tips.add(new TipText(specificTip.getMapTipDescription(), isSection(specificTip)));
                }
                for (MapTypeTipDTO typeTip :
                        typeTips) {
                    tips.add(new TipText(typeTip.getMapTipDescription(), isSection(typeTip)));
                }

                return new MapSubjectDetailsTipItemAdapter(getContext(), tips);
            }
        };
    }

    private Supplier<MapSubjectDetailsTipItemAdapter> createSupplierForAssociatedSubject(final MapSubjectDTO subject) {
        return new Supplier<MapSubjectDetailsTipItemAdapter>() {
            @Override
            public MapSubjectDetailsTipItemAdapter get() {
                List<MapSpecificTipDTO> specificTips;
                List<MapTypeTipDTO> typeTips;
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
                    specificTips = accessor.mapTipAccessor()
                            .GetMapSpecificTipsByMapSubject(differentAssociatedSubject.getMapSubjectId());
                    typeTips = accessor.mapTipAccessor()
                            .GetMapTypeTipsByMapSubject(differentAssociatedSubject.getMapSubjectId());
                }

                List<TipText> tips = Lists.newArrayList();
                for (MapSpecificTipDTO specificTip :
                        specificTips) {
                    tips.add(new TipText(specificTip.getMapTipDescription(), isSection(specificTip)));
                }
                for (MapTypeTipDTO typeTip :
                        typeTips) {
                    tips.add(new TipText(typeTip.getMapTipDescription(), isSection(typeTip)));
                }

                return new MapSubjectDetailsTipItemAdapter(getContext(), tips);
            }
        };
    }

//        List<MapSubjectHeader> groupHeaders = Lists.newArrayList();
//        List<MapTipsChild> mapTypeTips = getMapTypeTips(map);
//        if (mapTypeTips.size() > 0) {
//            groupHeaders.add(new MapSubjectHeader("Mode", mapTypeTips));
//        }
//        List<MapTipsChild> mapTips = getMapTips(map);
//        if (mapTips.size() > 0) {
//            groupHeaders.add(new MapSubjectHeader("Map", mapTips));
//        }
//        List<MapTipsChild> mapSegmentTips = getMapSegmentTipsForMap(map);
//        if (mapSegmentTips.size() > 0) {
//            // TODO: Query segments by Id to get names
//            groupHeaders.add(new MapSubjectHeader("Segment", mapSegmentTips));
//        }
//        List<MapTipsChild> mapLocationTips = getMapLocationTipsForMap(map);
//        if (mapLocationTips.size() > 0) {
//            // TODO: Query locations by Id to get names
//            groupHeaders.add(new MapSubjectHeader("Location", mapLocationTips));
//        }
//

}
