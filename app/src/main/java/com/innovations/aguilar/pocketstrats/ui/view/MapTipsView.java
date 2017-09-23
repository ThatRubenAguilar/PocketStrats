package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.sql.query.MapDatabaseOpenHelper;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;
import com.innovations.aguilar.pocketstrats.ui.MapSearchItemAdapter;
import com.innovations.aguilar.pocketstrats.ui.MapTipItemAdapter;
import com.innovations.aguilar.pocketstrats.ui.MapTipsChild;
import com.innovations.aguilar.pocketstrats.ui.MapTipsHeader;

import java.util.Collections;
import java.util.List;
import java.util.Map;

// TODO: Add tests for these views and how they act on deletion/restore
public class MapTipsView extends CoordinatorLayout {


    Supplier<MainPaneContainer> mainContainer;

    private TextView mapIcon;
    private TabLayout spawnTabs;
    private FloatingActionButton returnToTop;
    RecyclerView tipsList;

    Supplier<MapTipItemAdapter> attackSupplier;
    Supplier<MapTipItemAdapter> defendSupplier;

    public MapTipsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainContainer = Suppliers.memoize(new Supplier<MainPaneContainer>() {
            @Override
            public MainPaneContainer get() {
                return (MainPaneContainer)((MainActivity)getContext()).findViewById(R.id.layout_main_container);
            }
        });


        returnToTop = (FloatingActionButton)findViewById(R.id.button_return_to_top);
        mapIcon = (TextView)findViewById(R.id.map_icon);
        spawnTabs = (TabLayout)findViewById(R.id.spawn_tabs);
        tipsList = (RecyclerView)findViewById(R.id.list_tips);

        spawnTabs.addTab(configureTab(spawnTabs.newTab(), SpawnSide.Attack));
        spawnTabs.addTab(configureTab(spawnTabs.newTab(),SpawnSide.Defend));

        spawnTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SpawnSide side = SpawnSide.valueOf(tab.getText().toString());
                configureTipsForTab(side);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tipsList.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && returnToTop.isShown())
                    returnToTop.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    returnToTop.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        returnToTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapTipItemAdapter) tipsList.getAdapter()).collapseAll();
                returnToTop.hide();
            }
        });
    }


    TabLayout.Tab configureTab(TabLayout.Tab sideTab, SpawnSide side) {
        Context context = getContext();
        // TODO: Inflate custom textviews and setup like mapIcon.
        // See also: https://stackoverflow.com/questions/31640563/how-do-i-change-a-tab-background-color-when-using-tablayout
        //sideTab.setCustomView();
        sideTab.setText(side.toString());
        return sideTab;
    }

    public void loadTipsForMap(final MapDataDTO map) {

        configureMapIcon(map);

        attackSupplier = new Supplier<MapTipItemAdapter>() {
            @Override
            public MapTipItemAdapter get() {
                List<MapTipsHeader> groupHeaders = loadTipsForTab(map, SpawnSide.Attack);
                return new MapTipItemAdapter(getContext(), groupHeaders);
            }
        };
        defendSupplier = new Supplier<MapTipItemAdapter>() {
            @Override
            public MapTipItemAdapter get() {
                List<MapTipsHeader> groupHeaders = loadTipsForTab(map, SpawnSide.Defend);
                return new MapTipItemAdapter(getContext(), groupHeaders);
            }
        };

        tipsList.setLayoutManager(new LinearLayoutManager(getContext()));
        configureTipsForTab(SpawnSide.Attack);
    }

    private void configureTipsForTab(SpawnSide side) {
        if (side == SpawnSide.Attack)
            tipsList.setAdapter(attackSupplier.get());
        else if (side == SpawnSide.Defend)
            tipsList.setAdapter(defendSupplier.get());
        else
            Log.w(this.getClass().toString(), "None SpawnSide Configuration Attempt");
    }

    private void configureMapIcon(MapDataDTO map) {
        Context context = getContext();
        mapIcon.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));
        mapIcon.setText(map.getMapName());
        mapIcon.setBackground(DrawableCompat.unwrap(MapSearchItemAdapter.getItemBackground(context, map)));
    }


    List<MapTipsHeader> loadTipsForTab(MapDataDTO map, SpawnSide side) {
        List<MapSubjectDTO> subjects;
        List<MapSpecificTipDTO> specificTips;
        MapDatabaseOpenHelper openHelper = new MapDatabaseOpenHelper(getContext());
        try (SqlDataAccessor accessor = new SqlDataAccessor(openHelper.getReadableDatabase())) {
            subjects = accessor.GetMapSubjectsByMap(map.getMapId(), side);
            specificTips = accessor.GetMapSpecificTipsByMap(map.getMapId(), side);
        }

        Map<Integer, List<MapSpecificTipDTO>> subjectTipMap =
                generateSubjectTipMap(subjects, specificTips);

        List<MapTipsHeader> groupHeaders = Lists.newArrayList();

        for (MapSubjectDTO subject :
                subjects) {
            if (subjectTipMap.containsKey(subject.getMapSubjectId())) {
                List<MapTipsChild> children = generateChildMapTips(subjectTipMap, subject);
                MapTipsHeader header = new MapTipsHeader(subject, children);
                groupHeaders.add(header);
            }
            else
                Log.w(this.getClass().getName(), String.format("Missing Subject from Map '%s'", subject));
            // TODO: standardize logging and forward log4j to android log.
        }

        return groupHeaders;
    }

    @NonNull
    private List<MapTipsChild> generateChildMapTips(Map<Integer, List<MapSpecificTipDTO>> subjectTipMap, MapSubjectDTO subject) {
        List<MapSpecificTipDTO> childrenSpecificTips = subjectTipMap.get(subject.getMapSubjectId());
        List<MapTipsChild> children = Lists.newArrayList();
        for (MapSpecificTipDTO specificTip :
                childrenSpecificTips) {
            children.add(new MapTipsChild(specificTip.getMapTipDescription()));
        }
        return children;
    }

    private Map<Integer, List<MapSpecificTipDTO>> generateSubjectTipMap(List<MapSubjectDTO> subjects,
                                                                        List<MapSpecificTipDTO> specificTips) {
        Map<Integer, List<MapSpecificTipDTO>> subjectTipMap = Maps.newHashMap();
        for (MapSubjectDTO subject :
                subjects) {
            subjectTipMap.put(new Integer(subject.getMapSubjectId()), Lists.<MapSpecificTipDTO>newArrayList());
        }

        for (MapSpecificTipDTO specificTip :
                specificTips) {
            if (subjectTipMap.containsKey(specificTip.getMapSubjectId()))
                subjectTipMap.get(specificTip.getMapSubjectId()).add(specificTip);
            else
                Log.w(this.getClass().getName(), String.format("Orphaned SpecificTip '%s'", specificTip));
            // TODO: standardize logging and forward log4j to android log.
        }
        return subjectTipMap;
    }


//        List<MapTipsHeader> groupHeaders = Lists.newArrayList();
//        List<MapTipsChild> mapTypeTips = getMapTypeTips(map);
//        if (mapTypeTips.size() > 0) {
//            groupHeaders.add(new MapTipsHeader("Mode", mapTypeTips));
//        }
//        List<MapTipsChild> mapTips = getMapTips(map);
//        if (mapTips.size() > 0) {
//            groupHeaders.add(new MapTipsHeader("Map", mapTips));
//        }
//        List<MapTipsChild> mapSegmentTips = getMapSegmentTipsForMap(map);
//        if (mapSegmentTips.size() > 0) {
//            // TODO: Query segments by Id to get names
//            groupHeaders.add(new MapTipsHeader("Segment", mapSegmentTips));
//        }
//        List<MapTipsChild> mapLocationTips = getMapLocationTipsForMap(map);
//        if (mapLocationTips.size() > 0) {
//            // TODO: Query locations by Id to get names
//            groupHeaders.add(new MapTipsHeader("Location", mapLocationTips));
//        }
//

    // TODO: Show floating button if one section is open and scrolling down has occurred


}
