package com.innovations.aguilar.pocketstrats.sql.query;

import android.database.Cursor;

/**
 * Created by Ruben on 9/17/2017.
 */
public interface DTOFromCursorFactory<T> {
    T Create(Cursor c);
}
