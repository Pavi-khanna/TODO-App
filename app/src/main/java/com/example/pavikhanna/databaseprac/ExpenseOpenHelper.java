package com.example.pavikhanna.databaseprac;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pavi Khanna on 2/17/2018.
 */

public class ExpenseOpenHelper extends SQLiteOpenHelper {
    public ExpenseOpenHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String expensesSql = "CREATE TABLE " + Contract.Expenses.TABLE_NAME  + " ( " +
                Contract.Expenses.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.Expenses.TITLE + " TEXT, " +
                Contract.Expenses.DESCRIPTION + " TEXT, " +
                Contract.Expenses.AMOUNT + " INTEGER)";
        sqLiteDatabase.execSQL(expensesSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
