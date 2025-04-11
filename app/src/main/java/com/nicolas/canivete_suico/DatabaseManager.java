package com.nicolas.canivete_suico;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "picture_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "picture";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "base64 TEXT, " +
            "date DATETIME);";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertPicture(Picture picture) {
        String base64 = picture.getBase64();
        String date = picture.getDate();

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_NAME + " (base64, date) VALUES (?, ?)";
        db.execSQL(sql, new Object[]{base64, base64});
        db.close();
    }

    public List<Picture> getAllPictures() {
        List<Picture> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT name, number FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String base64 = cursor.getString(0);
                String date = cursor.getString(1);
                lista.add(new Picture(base64, date));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

    public void deletePictureById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE id = ?", new Object[]{id});
        db.close();
    }

    public void cleanData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
}