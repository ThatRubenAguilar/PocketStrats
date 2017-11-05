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
import android.widget.LinearLayout;

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
import com.innovations.aguilar.pocketstrats.ui.OnIndexClickListener;
import com.innovations.aguilar.pocketstrats.ui.adapter.MapSubjectDisplayItemAdapter;
import com.innovations.aguilar.pocketstrats.ui.adapter.MapSubjectItemAdapter;
import com.innovations.aguilar.pocketstrats.ui.OnDataClickListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// TODO: Add tests for these views and how they act on deletion/restore
public class MapSubjectsView extends CoordinatorLayout implements Container {
    protected static Logger log = LoggerFactory.getLogger(MapSubjectsView.class);

    Supplier<MainPaneContainer> mainContainer;

    RecyclerView subjectsList;
    MapSpawnTabLayout tabLayout;

    LinearLayout subjectDetailsLayout;
    SwipeListDisplayView subjectListDisplay;
    MapSubjectsDetailsView subjectDetails;

    boolean detailsView = false;

    Supplier<List<MapSubjectDTO>> attackDataSupplier;
    Supplier<List<MapSubjectDTO>> defendDataSupplier;
    Supplier<MapSubjectItemAdapter> attackItemAdapterSupplier;
    Supplier<MapSubjectItemAdapter> defendItemAdapterSupplier;
    Supplier<MapSubjectDisplayItemAdapter> attackDisplayAdapterSupplier;
    Supplier<MapSubjectDisplayItemAdapter> defendDisplayAdapterSupplier;

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
        subjectListDisplay = (SwipeListDisplayView) findViewById(R.id.list_subjects_display);
        subjectDetailsLayout = (LinearLayout) findViewById(R.id.layout_view_subject_details);
        tabLayout.removeView(subjectsList);
        setDetailsView(false);

        tabLayout.addOnTabSelectedListener(new MapSpawnTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab, SpawnSide sideSelected) {
                if (detailsView) {
                    subjectDetails.loadSubjectDetailsForSide(sideSelected);
                    loadSwipeListForSide(sideSelected);
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

        createDataSuppliers(map);

        OnIndexClickListener clickListener = new OnIndexClickListener() {
            @Override
            public void onIndexClick(View v, int index) {
                if (tabLayout.getSelectedSpawnSide() == SpawnSide.Attack) {
                    MapSubjectDTO subject = attackDataSupplier.get().get(index);
                    subjectDetails.loadSubjectDetails(subject);
                } else if (tabLayout.getSelectedSpawnSide() == SpawnSide.Defend) {
                    MapSubjectDTO subject = defendDataSupplier.get().get(index);
                    subjectDetails.loadSubjectDetails(subject);
                } else {
                    log.warn("SpawnSide unknown in onIndexClick");
                }
            }
        };
        subjectListDisplay.setOnNextClickListener(clickListener);
        subjectListDisplay.setOnPrevClickListener(clickListener);

        // TODO: Add left/right swipe for subject details

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setAutoMeasureEnabled(true);
        subjectsList.setLayoutManager(manager);
        configureSubjectsForTab(SpawnSide.Attack);
    }

    private void createDataSuppliers(final MapDataDTO map) {
        final OnDataClickListener<MapSubjectDTO> itemClicked = new OnDataClickListener<MapSubjectDTO>() {
            @Override
            public void onClick(View v, MapSubjectDTO data) {
                setDetailsView(true);
                subjectDetails.loadSubjectDetails(data);
                loadSubjectSwipeList(data);
            }
        };

        attackDataSupplier = Suppliers.memoize(new Supplier<List<MapSubjectDTO>>() {
            @Override
            public List<MapSubjectDTO> get() {
                return loadSubjectsForTab(map, SpawnSide.Attack);
            }
        });
        defendDataSupplier = Suppliers.memoize(new Supplier<List<MapSubjectDTO>>() {
            @Override
            public List<MapSubjectDTO> get() {
                return loadSubjectsForTab(map, SpawnSide.Defend);
            }
        });

        attackItemAdapterSupplier = Suppliers.memoize(new Supplier<MapSubjectItemAdapter>() {
            @Override
            public MapSubjectItemAdapter get() {
                List<MapSubjectDTO> groupSubjects = attackDataSupplier.get();
                MapSubjectItemAdapter adapter = new MapSubjectItemAdapter(getContext(), groupSubjects);
                adapter.setOnItemClickListener(itemClicked);
                return adapter;
            }
        });
        defendItemAdapterSupplier = Suppliers.memoize(new Supplier<MapSubjectItemAdapter>() {
            @Override
            public MapSubjectItemAdapter get() {
                List<MapSubjectDTO> groupSubjects = defendDataSupplier.get();
                MapSubjectItemAdapter adapter =  new MapSubjectItemAdapter(getContext(), groupSubjects);
                adapter.setOnItemClickListener(itemClicked);
                return adapter;
            }
        });


        attackDisplayAdapterSupplier = Suppliers.memoize(new Supplier<MapSubjectDisplayItemAdapter>() {
            @Override
            public MapSubjectDisplayItemAdapter get() {
                List<MapSubjectDTO> groupSubjects = attackDataSupplier.get();
                MapSubjectDisplayItemAdapter adapter = new MapSubjectDisplayItemAdapter(getContext(), groupSubjects);
                return adapter;
            }
        });
        defendDisplayAdapterSupplier = Suppliers.memoize(new Supplier<MapSubjectDisplayItemAdapter>() {
            @Override
            public MapSubjectDisplayItemAdapter get() {
                List<MapSubjectDTO> groupSubjects = defendDataSupplier.get();
                MapSubjectDisplayItemAdapter adapter =  new MapSubjectDisplayItemAdapter(getContext(), groupSubjects);
                return adapter;
            }
        });
    }

    // TODO: Extract to view configurator when you have a generic widget vs specific setup
    private void loadSubjectSwipeList(MapSubjectDTO data) {
        if (data.getSpawnSideId() == SpawnSide.Attack) {
            int selectedIndex = attackDataSupplier.get().indexOf(data);
            subjectListDisplay.setDataAdapter(attackDisplayAdapterSupplier.get(), selectedIndex);
        } else if (data.getSpawnSideId() == SpawnSide.Defend) {
            int selectedIndex = defendDataSupplier.get().indexOf(data);
            subjectListDisplay.setDataAdapter(defendDisplayAdapterSupplier.get(), selectedIndex);
        } else {
            log.warn("SpawnSide unknown in loadSubjectSwipeList");
        }
    }

    // TODO: Extract to view configurator when you have a generic widget vs specific setup
    private void loadSwipeListForSide(SpawnSide side) {
        int currentSelectedIndex = subjectListDisplay.getSelectedIndex();
        if (side == SpawnSide.Attack) {
            subjectListDisplay.setDataAdapter(attackDisplayAdapterSupplier.get(), currentSelectedIndex);
        } else if (side == SpawnSide.Defend) {
            subjectListDisplay.setDataAdapter(defendDisplayAdapterSupplier.get(), currentSelectedIndex);
        } else {
            log.warn("SpawnSide unknown in loadSubjectSwipeList");
        }
    }

    private void configureSubjectsForTab(SpawnSide side) {
        if (side == SpawnSide.Attack)
            subjectsList.setAdapter(attackItemAdapterSupplier.get());
        else if (side == SpawnSide.Defend)
            subjectsList.setAdapter(defendItemAdapterSupplier.get());
        else
            log.warn("None SpawnSide Configuration Attempt");
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
            tabLayout.addView(subjectDetailsLayout);
        } else {
            tabLayout.removeView(subjectDetailsLayout);
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
