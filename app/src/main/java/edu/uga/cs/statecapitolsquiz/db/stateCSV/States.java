package edu.uga.cs.statecapitolsquiz.db.stateCSV;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class States {
    private SQLiteDatabase db;
    private StatesHelper dbHelper;

    //for testing
    public States(){};

    public States(Context context) {
        dbHelper = new StatesHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Insert a single state and capital into the database
    public void insertState(String state, String capital) {
        ContentValues values = new ContentValues();
        values.put(StatesHelper.COLUMN_STATE_NAME, state);
        values.put(StatesHelper.COLUMN_CAPITAL_NAME, capital);
        long id = db.insert(StatesHelper.TABLE_STATES, null, values);
        Log.d("StatesDbManager", "Inserted state with ID: " + id);
    }
}
