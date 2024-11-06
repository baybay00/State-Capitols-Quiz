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

    // Read CSV file from assets and insert all states
    public void insertAllStatesFromCSV(Context context) {
        InputStream inputStream = null;
        BufferedReader reader = null;

        try {
            // Open the CSV file from assets
            inputStream = context.getAssets().open("states_capitals.csv");
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Split each line by comma to get state and capital
                String[] stateCapital = line.split(",");
                if (stateCapital.length == 2) {
                    String state = stateCapital[0].trim();
                    String capital = stateCapital[1].trim();

                    System.out.println(state + " " + capital);

                    // Insert the state and capital into the database
                    insertState(state, capital);
                }
            }
        } catch (IOException e) {
            Log.e("States", "Error reading CSV file", e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                Log.e("States", "Error closing file streams", e);
            }
        }
    }
}
