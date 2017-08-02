package com.innovations.aguilar.pocketstrats.ui.view;

import com.innovations.aguilar.pocketstrats.dto.MapDataDTO;

public interface ViewDisplayer<TData> {
    void showView(TData data);
}
