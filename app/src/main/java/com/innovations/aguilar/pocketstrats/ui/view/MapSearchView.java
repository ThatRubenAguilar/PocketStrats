package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.ui.EnumSetToggleFilterClickListener;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;
import com.innovations.aguilar.pocketstrats.ui.filter.MapItemFilterData;
import com.innovations.aguilar.pocketstrats.ui.MapSearchItemAdapter;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.dto.MapType;
import com.innovations.aguilar.pocketstrats.query.MapDataAccessor;
import com.innovations.aguilar.pocketstrats.query.MapDatabaseOpenHelper;

import java.util.List;

public class MapSearchView extends LinearLayout {

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
        try (MapDataAccessor accessor = new MapDataAccessor(openHelper.getReadableDatabase())) {
            maps = accessor.GetAllMaps();
        }

        final MapSearchItemAdapter mapAdapter = new MapSearchItemAdapter(getContext(), maps);
        viewMapsList = (ListView) findViewById(R.id.list_maps);
        viewMapsList.setAdapter(mapAdapter);
        viewMapsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListViewItem<MapDataDTO> listItem = (ListViewItem<MapDataDTO>)view;
                Log.d("View List", String.format("Clicked '%s'", listItem.getItemData().getMapName()));
                showTipsView(listItem.getItemData());
            }
        });


        buttonAssault = (Button)findViewById(R.id.button_filter_assault);
        buttonAssault.setOnClickListener(
                new EnumSetToggleFilterClickListener<MapType, MapItemFilterData>(
                        MapType.Assault, mapAdapter.getMapFilter()));

        buttonControl = (Button)findViewById(R.id.button_filter_control);
        buttonControl.setOnClickListener(
                new EnumSetToggleFilterClickListener<MapType, MapItemFilterData>(
                        MapType.Control, mapAdapter.getMapFilter()));

        buttonEscort = (Button)findViewById(R.id.button_filter_escort);
        buttonEscort.setOnClickListener(
                new EnumSetToggleFilterClickListener<MapType, MapItemFilterData>(
                        MapType.Escort, mapAdapter.getMapFilter()));

        buttonHybrid_AE = (Button)findViewById(R.id.button_filter_hybrid_ae);
        buttonHybrid_AE.setOnClickListener(
                new EnumSetToggleFilterClickListener<MapType, MapItemFilterData>(
                        MapType.Hybrid_Assault_Escort, mapAdapter.getMapFilter()));

        // TODO: Add all autocomplete functionality, currently only filters
        textMapSearch = (AutoCompleteTextView)findViewById(R.id.text_map_search_autocomplete);
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

    void showTipsView(MapDataDTO map) {
        mainContainer.get().removeViewToBackStack(this);
        View rootView = View.inflate(getContext(), R.layout.map_tips, mainContainer.get());
        MapTipsView tipsView = (MapTipsView) rootView.findViewById(R.id.layout_map_tips);
        tipsView.loadTipsForMap(map);
    }

    class ModeSelectionPresenter {

        public ModeSelectionPresenter() {

        }
    }

}
