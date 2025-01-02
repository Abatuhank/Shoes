package com.example.shoes.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shoes.Shoe;

import java.util.ArrayList;

public class ShoeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Shoes.db"; // Veritabanı adı
    private static final int DATABASE_VERSION = 1; // Versiyon

    // Tablo ve sütun adları
    public static final String TABLE_SHOES = "shoes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_YOU_URL = "youUrl";

    // Tablo oluşturma sorgusu
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_SHOES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_SIZE + " INTEGER, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_IMAGE_URL + " TEXT, " +
                    COLUMN_YOU_URL + " TEXT);";

    public ShoeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE); // Veritabanı ve tabloyu oluştur
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Tabloyu güncelle (örneğin, eski versiyondaki veriler silinecek)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOES);
        onCreate(db);
    }

    // Verileri Çekme
    public ArrayList<Shoe> getAllShoes() {
        ArrayList<Shoe> shoeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Sorgu
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SHOES, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") int size = cursor.getInt(cursor.getColumnIndex(COLUMN_SIZE));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL));
                @SuppressLint("Range") String youUrl = cursor.getString(cursor.getColumnIndex(COLUMN_YOU_URL));

                // Listeye ekle
                Shoe shoe = new Shoe(name, size, price, imageUrl, youUrl);
                shoeList.add(shoe);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return shoeList; // Listeyi döndür
    }
}
