package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.innovations.aguilar.pocketstrats.ui.Container;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.listener.EnumSetToggleFilterClickListener;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.filter.MapItemFilterData;
import com.innovations.aguilar.pocketstrats.ui.adapter.MapSearchItemAdapter;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.sql.query.MapDatabaseOpenHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MapSearchView extends LinearLayout implements ViewDisplayer<MapDataDTO> {
    protected static Logger log = LoggerFactory.getLogger(MapSearchView.class);
    public static final String MapSearchViewRootTag = "MAP_SEARCH_ROOT";

    Button buttonAssault;
    Button buttonControl;
    Button buttonEscort;
    Button buttonHybrid_AE;
    AutoCompleteTextView textMapSearch;
    ListView viewMapsList;

    Supplier<Container> mainContainer;

    public MapSearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainContainer = MainActivity.generateContainerRef(this);

        List<MapDataDTO> maps;
        MapDatabaseOpenHelper openHelper = MapDatabaseOpenHelper.getHelper(getContext());
        try (SqlDataAccessor accessor = new SqlDataAccessor(openHelper.getReadableDatabase())) {
            maps = accessor.mapAccessor().GetAllMaps();
        }

        final MapSearchItemAdapter mapAdapter = new MapSearchItemAdapter(getContext(), maps, this);
        viewMapsList = (ListView) findViewById(R.id.list_maps);
        viewMapsList.setAdapter(mapAdapter);


        buttonAssault = (Button)findViewById(R.id.button_filter_assault);
        configureFilterButton(buttonAssault, MapType.Assault, mapAdapter);

        buttonControl = (Button)findViewById(R.id.button_filter_control);
        configureFilterButton(buttonControl, MapType.Control, mapAdapter);

        buttonEscort = (Button)findViewById(R.id.button_filter_escort);
        configureFilterButton(buttonEscort, MapType.Escort, mapAdapter);

        buttonHybrid_AE = (Button)findViewById(R.id.button_filter_hybrid_ae);
        configureFilterButton(buttonHybrid_AE, MapType.Hybrid_Assault_Escort, mapAdapter);

        // TODO: Add all autocomplete functionality, currently only filters
        textMapSearch = (AutoCompleteTextView)findViewById(R.id.text_map_search_autocomplete);
        textMapSearch.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(getContext().getAssets()));
        textMapSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                MapItemFilterData filterData = mapAdapter.getMapFilter().getFilterData();
                filterData.setMapNameFilter(editable);
                mapAdapter.getMapFilter().filter(filterData);
                log.debug("Filtering '{}'", editable);
            }
        });

    }

    private void configureFilterButton(Button button, MapType mapType, MapSearchItemAdapter mapAdapter) {
        button.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(getContext().getAssets()));
        button.setOnClickListener(
                new EnumSetToggleFilterClickListener<MapType, MapItemFilterData>(
                        mapType, mapAdapter.getMapFilter()));
    }

    public void showView(MapDataDTO map) {
        showTipsView(map);
    }
    void showTipsView(MapDataDTO map) {
        mainContainer.get().getBackStackManager().removeViewToBackStack(this, MapSearchViewRootTag);
        View rootView = View.inflate(getContext(), R.layout.map_subjects, mainContainer.get().getViewGroup());
        MapSubjectsView tipsView = (MapSubjectsView) rootView.findViewById(R.id.layout_map_subjects);
        tipsView.loadSubjectsForMap(map);
    }

    public static class MapSearchPresenter {
        private Container container;
        private View fromView;

        public MapSearchPresenter(Container container, View fromView) {
            Preconditions.checkNotNull(container, "container cannot be null");
            Preconditions.checkNotNull(fromView, "fromView cannot be null");
            this.container = container;
            this.fromView = fromView;
        }

        public void presentMapSearch() {
            presentMapSearch(true);
        }
        public void presentMapSearch(boolean fromToBackStack) {
            if (fromToBackStack)
                container.getBackStackManager().removeViewToBackStack(fromView);
            View.inflate(container.getViewGroup().getContext(), R.layout.map_search, container.getViewGroup());
        }
    }
}
