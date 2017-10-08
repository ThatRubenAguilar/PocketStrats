package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.sql.query.MapDatabaseOpenHelper;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.ui.Container;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;
import com.innovations.aguilar.pocketstrats.ui.adapter.MapSubjectItemAdapter;
import com.innovations.aguilar.pocketstrats.ui.OnDataClickListener;

import java.util.List;

// TODO: Add tests for these views and how they act on deletion/restore
public class MapSubjectsView extends CoordinatorLayout implements Container {

    Supplier<MainPaneContainer> mainContainer;

    RecyclerView subjectsList;
    MapSpawnTabLayout tabLayout;
    MapSubjectsDetailsView subjectDetails;

    boolean detailsView = false;

    Supplier<MapSubjectItemAdapter> attackSupplier;
    Supplier<MapSubjectItemAdapter> defendSupplier;

    public MapSubjectsView(Context context) {
        super(context);
    }
    public MapSubjectsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MapSubjectsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

        tabLayout = (MapSpawnTabLayout)findViewById(R.id.map_spawn_layout);
        subjectsList = (RecyclerView)findViewById(R.id.list_subjects);
        subjectDetails = (MapSubjectsDetailsView) findViewById(R.id.view_subject_details);
        tabLayout.removeView(subjectsList);
        setDetailsView(false);

        tabLayout.addOnTabSelectedListener(new MapSpawnTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab, SpawnSide sideSelected) {
                if (detailsView) {
                    subjectDetails.loadSubjectDetailsForSide(sideSelected);
                }
                else {
                    configureSubjectsForTab(sideSelected);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab, SpawnSide sideUnselected) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab, SpawnSide sideReselected) {

            }
        });
    }

    public void loadSubjectsForMap(final MapDataDTO map) {

        tabLayout.setMapIcon(map);

        final OnDataClickListener<MapSubjectDTO> itemClicked = new OnDataClickListener<MapSubjectDTO>() {
            @Override
            public void onClick(View v, MapSubjectDTO data) {
                setDetailsView(true);
                subjectDetails.loadSubjectDetails(data);
            }
        };

        attackSupplier = new Supplier<MapSubjectItemAdapter>() {
            @Override
            public MapSubjectItemAdapter get() {
                List<MapSubjectDTO> groupSubjects = loadSubjectsForTab(map, SpawnSide.Attack);
                MapSubjectItemAdapter adapter = new MapSubjectItemAdapter(getContext(), groupSubjects);
                adapter.setOnItemClickListener(itemClicked);
                return adapter;
            }
        };
        defendSupplier = new Supplier<MapSubjectItemAdapter>() {
            @Override
            public MapSubjectItemAdapter get() {
                List<MapSubjectDTO> groupSubjects = loadSubjectsForTab(map, SpawnSide.Defend);
                MapSubjectItemAdapter adapter =  new MapSubjectItemAdapter(getContext(), groupSubjects);
                adapter.setOnItemClickListener(itemClicked);
                return adapter;
            }
        };

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setAutoMeasureEnabled(true);
        subjectsList.setLayoutManager(manager);
        configureSubjectsForTab(SpawnSide.Attack);
    }

    private void configureSubjectsForTab(SpawnSide side) {
        if (side == SpawnSide.Attack)
            subjectsList.setAdapter(attackSupplier.get());
        else if (side == SpawnSide.Defend)
            subjectsList.setAdapter(defendSupplier.get());
        else
            Log.w(this.getClass().toString(), "None SpawnSide Configuration Attempt");
    }



    List<MapSubjectDTO> loadSubjectsForTab(MapDataDTO map, SpawnSide side) {
        List<MapSubjectDTO> subjects;
        MapDatabaseOpenHelper openHelper = new MapDatabaseOpenHelper(getContext());
        try (SqlDataAccessor accessor = new SqlDataAccessor(openHelper.getReadableDatabase())) {
            subjects = accessor.mapSubjectAccessor().GetMapSubjectsByMapOrSide(map.getMapId(), side);
        }

        return subjects;
    }

    void setDetailsView(boolean enabled) {
        if (enabled) {
            tabLayout.removeView(subjectsList);
            tabLayout.addView(subjectDetails);
        } else {
            tabLayout.removeView(subjectDetails);
            tabLayout.addView(subjectsList);
        }
        detailsView = enabled;
    }

    @Override
    public boolean onBackPressed() {
        if (detailsView) {
            setDetailsView(false);
            configureSubjectsForTab(tabLayout.getSelectedSpawnSide());
            return true;
        }
        return false;
    }

    @Override
    public void removeViewToBackStack(View view) {

    }

}
