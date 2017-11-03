package com.innovations.aguilar.pocketstrats.sql.query;

import android.content.Context;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Ruben on 10/19/2017.
 */
public class OwnedSqlDataAccessor implements Closeable {
    public SqlDataAccessor getAccessor() {
        return accessor;
    }

    private final SqlDataAccessor accessor;
    private final boolean ownAccessor;

    public OwnedSqlDataAccessor(Context context) {
        this.ownAccessor = true;
        MapDatabaseOpenHelper openHelper = new MapDatabaseOpenHelper(context);
        this.accessor = new SqlDataAccessor(openHelper.getReadableDatabase());

    }

    public OwnedSqlDataAccessor(SqlDataAccessor accessor) {
        this.ownAccessor = false;
        this.accessor = accessor;
    }

    @Override
    public void close() throws IOException {
        if (ownAccessor && accessor != null)
            accessor.close();
    }
}
