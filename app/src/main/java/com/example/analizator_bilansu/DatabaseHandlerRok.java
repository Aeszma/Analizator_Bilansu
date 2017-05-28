package com.example.analizator_bilansu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerRok extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Roki.db";
    private static final String TABLE_NAME = "roki";

    private static final String KEY_YEAR = "rok";

    private SQLiteDatabase db;

    public DatabaseHandlerRok(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_YEAR + " String PRIMARY KEY)";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);
    }

    public void addYear(Rok r) {
        ContentValues values = new ContentValues();
        values.put(KEY_YEAR, r.getRok());
    System.out.println("Dodano rok " + " " + r.getRok());
        db.insert(TABLE_NAME, null, values);
    }

    public Rok getYear(String rok) {
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_YEAR},
                KEY_YEAR + "=?", new String[]{rok}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Rok r = new Rok(cursor.getString(0));

        return r;
    }

    public List<Rok> getAll() {

        List<Rok> yearsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Rok r = new Rok();
                r.setRok(cursor.getString(0));

                yearsList.add(r);
            } while (cursor.moveToNext());
        }

        return yearsList;
    }

    public int getYearsCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    public int updateAsset(Rok r) {
        ContentValues values = new ContentValues();
        values.put(KEY_YEAR, r.getRok());

        return db.update(TABLE_NAME, values, KEY_YEAR + " = ?", new String[]{r.getRok()});
    }

    public void deleteYear(Rok r) {
        db.delete(TABLE_NAME, KEY_YEAR + " = ?",
                new String[]{r.getRok()});
    }
}
