package com.example.carsapp_week2.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class CarContentProvider extends ContentProvider {
    carDB db;
    carDao cd;
    private final String carTable = "Cars";
    public static final String CONTENT_AUTHORITY = "carApp.contpro";
    public static final Uri CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public CarContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deletionCount;
        deletionCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .delete(carTable, selection, selectionArgs);
        return deletionCount;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = db
                // accesses the database you need
                .getOpenHelper()
                // manipulates the database
                .getWritableDatabase()

                // content value is a container that will  be used in another app for using the data
                .insert(carTable, 0, values);
        return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

    @Override
    public boolean onCreate() {
        // carDb instantiated
        db = carDB.getDatabase(getContext());
        cd = db.CarDao();
        return true;
    }

    @Override
    // uri is the address to the table(for updates, applies for all the cursors)
    // cursor is the query of the object, its more a return type object
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(carTable);
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null);
        final Cursor cursor = db
                .getOpenHelper()
                .getReadableDatabase()
                .query(query, selectionArgs);
        return cursor;
    }
    // MUA has the dao method, so you can create the access here so that it can be called else where

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updateCount;
        updateCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .update(carTable, 0, values, selection, selectionArgs);
        return updateCount;
    }
}