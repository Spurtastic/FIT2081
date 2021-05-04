package com.example.wk2pt2.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

public class CarsContentProvider extends ContentProvider {
    public static final String CONTENT_AUTHORITY = "fit2081.app.Jason";

//    public static final Uri CONTENT_URI = Uri.parse("content://" +  CONTENT_AUTHORITY);
    public static final Uri CONTENT_URI = Uri.parse("content://fit2081.app.Jason");

    Uri uri = Uri.parse("content://fit2081.app.Jason/cars");


    public static final int MULTIPLE_ROW_CARS = 1;
    public static final int SINGLE_ROW_CARS = 2;

    CarDatabase db;
    private static final UriMatcher oUriMatcher = createUriMatcher();

    public static UriMatcher createUriMatcher(){
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, Car.TABLE_NAME, MULTIPLE_ROW_CARS);

        uriMatcher.addURI(authority, Car.TABLE_NAME +  "/#", SINGLE_ROW_CARS);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        db = CarDatabase.getDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    //URI is an address | Projection is basically telling which table to take in the DB | Selection is the where clause in SQL statement |
    // SelectionArgs are the parameters in where clause, need to use ? because SQL injection | sortOrder ASC, DESC
    //Cursor is a row in the DB that matches all the conditions, Cursor stores a pointer to the row that we are currently at
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Log.d("Week8", "** QUERY METHOD CALLED");

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Car.TABLE_NAME);
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null);

        final Cursor cursor = db
                .getOpenHelper()
                .getReadableDatabase()
                .query(query, selectionArgs);


        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long rowId = db.getOpenHelper().getWritableDatabase().insert(Car.TABLE_NAME, 0 , values);
        return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }




    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int updatedRows;

        updatedRows = db
                .getOpenHelper()
                .getWritableDatabase()
                .update(Car.TABLE_NAME,0,values, selection, selectionArgs);

        return updatedRows;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deletedRows;

        deletedRows =
                db.getOpenHelper()
                        .getWritableDatabase()
                        .delete(Car.TABLE_NAME, selection, selectionArgs);

        return deletedRows;

    }

}