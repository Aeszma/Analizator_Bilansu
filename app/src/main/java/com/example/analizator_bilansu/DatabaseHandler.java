package com.example.analizator_bilansu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Wskaznik.db";
    private static final String TABLE_NAME = "wskaznik";

    private static final String KEY_NAME = "nazwa";
    private static final String KEY_VALUE = "wartoscObecna";
    private static final String KEY_VALUE_PREVIOUS = "wartoscUbiegla";
    private static final String KEY_YEAR = "rok";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_YEAR +
                " TEXT, " + KEY_NAME + " TEXT, " + KEY_VALUE + " TEXT, " + KEY_VALUE_PREVIOUS
                + " FLOAT)";
        db.execSQL(CREATE_NOTES_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);
    }

    public void addValue(Wskazniki w) {
        ContentValues values = new ContentValues();
        values.put(KEY_YEAR, w.getRok());
        values.put(KEY_NAME, w.getNazwa());
        values.put(KEY_VALUE, w.getWartoscObecna());
        values.put(KEY_VALUE_PREVIOUS, w.getWartoscUbiegla());

        db.insert(TABLE_NAME, null, values);
    }

    public Wskazniki getValue(String rok) {
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_YEAR, KEY_NAME, KEY_VALUE, KEY_VALUE_PREVIOUS},
                KEY_YEAR + "-?", new String[]{rok}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Wskazniki w = new Wskazniki(cursor.getString(0),
                cursor.getString(1), Float.parseFloat(cursor.getString(2)), Float.parseFloat(cursor.getString(3)));

        return w;
    }

    public Wskazniki getValueA(String rok, String nazwa) {


        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_YEAR, KEY_NAME, KEY_VALUE, KEY_VALUE_PREVIOUS},
                KEY_YEAR + "=?" + " AND " + KEY_NAME + "=?", new String[]{rok, nazwa}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Wskazniki w = new Wskazniki(cursor.getString(0), cursor.getString(1), Float.parseFloat(cursor.getString(2)), Float.parseFloat(cursor.getString(3)));
        return w;
    }

    public List<Wskazniki> getAll() {

        List<Wskazniki> assetList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Wskazniki w = new Wskazniki();
                w.setRok(cursor.getString(0));
                w.setNazwa(cursor.getString(1));
                w.setWartoscObecna(Float.parseFloat(cursor.getString(2)));
                w.setWartoscUbiegla(Float.parseFloat(cursor.getString(3)));

                assetList.add(w);
            } while (cursor.moveToNext());
        }

        return assetList;
    }

    public int getValuesCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    public int updateValue(Wskazniki w) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, w.getNazwa());
        values.put(KEY_VALUE, w.getWartoscObecna());
        values.put(KEY_VALUE_PREVIOUS, w.getWartoscUbiegla());
        values.put(KEY_YEAR, w.getRok());

        return db.update(TABLE_NAME, values, KEY_YEAR + " = ?", new String[]{w.getRok()});
    }

    public void deleteValue(Wskazniki w) {
        db.delete(TABLE_NAME, KEY_YEAR + " = ?",
                new String[]{w.getRok()});
    }
}
