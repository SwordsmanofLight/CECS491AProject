package com.example.MHB.prototype2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "pregnant.db";
    public static final String TABLE_NAME = "Items";

    public static final String COL_1 = "UPC";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Brand";
    public static final String COL_4 = "Size";
    public static final String COL_5 = "Ingredients";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                "UPC          TEXT     NOT NULL PRIMARY KEY," +
                "Name         TEXT     NOT NULL," +
                "Brand        TEXT     NOT NULL," +
                "Size         TEXT     NOT NULL," +
                "Ingredients  TEXT     NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String UPC, String name, String brand, String size, String ingredients)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, UPC);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, brand);
        contentValues.put(COL_4, size);
        contentValues.put(COL_5, ingredients);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);

        return res;
    }
}