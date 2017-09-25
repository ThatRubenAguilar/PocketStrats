package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.EnumSetToggleFilterClickListener;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;
import com.innovations.aguilar.pocketstrats.ui.filter.MapItemFilterData;
import com.innovations.aguilar.pocketstrats.ui.MapSearchItemAdapter;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.sql.query.MapDatabaseOpenHelper;

import java.util.List;

public class MapSearchView extends LinearLayout implements ViewDisplayer<MapDataDTO> {

    Button buttonAssault;
    Button buttonControl;
    Button buttonEscort;
    Button buttonHybrid_AE;
    AutoCompleteTextView textMapSearch;
    ListView viewMapsList;
    ModeSelectionPresenter presenter;

    Supplier<MainPaneContainer> mainContainer;

    public MapSearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        presenter = new ModeSelectionPresenter();
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

        List<MapDataDTO> maps;
        MapDatabaseOpenHelper openHelper = new MapDatabaseOpenHelper(getContext());
        try (SqlDataAccessor accessor = new SqlDataAccessor(openHelper.getReadableDatabase())) {
            maps = accessor.GetAllMaps();
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
                Log.d("Filter List", String.format("Filtering '%s'", editable));
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
        mainContainer.get().removeViewToBackStack(this);
        View rootView = View.inflate(getContext(), R.layout.map_subjects, mainContainer.get());
        MapSubjectsView tipsView = (MapSubjectsView) rootView.findViewById(R.id.layout_map_tips);
        tipsView.loadTipsForMap(map);
    }

    class ModeSelectionPresenter {

        public ModeSelectionPresenter() {

        }
    }

}
