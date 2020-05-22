package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.data.PetContract.PetEntry;

public class PetDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "shelter.db";
    public static final int DATABASE_VERSION = 1;

    private static final String INT__PRIMARY_AUTO = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    private static final String TEXT_NOT_NULL = " TEXT NOT NULL, ";
    private static final String INT_NOT_NULL = " INTEGER NOT NULL, ";
    private static final String INT_NOT_NULL_END = " INTEGER NOT NULL";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PetEntry.TABLE_NAME + " (" +
            PetEntry.COLUMN_ID + INT__PRIMARY_AUTO +
            PetEntry.COLUMN_NAME + TEXT_NOT_NULL +
            PetEntry.COLUMN_GENDER + INT_NOT_NULL +
            PetEntry.COLUMN_BREED + TEXT_NOT_NULL +
            PetEntry.COLUMN_WEIGHT + INT_NOT_NULL_END + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PetEntry.TABLE_NAME;


    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
