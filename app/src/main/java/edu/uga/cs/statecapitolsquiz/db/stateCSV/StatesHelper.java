package edu.uga.cs.statecapitolsquiz.db.stateCSV;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class StatesHelper extends SQLiteOpenHelper {

    // Database version and name
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "states_and_capitals.db";

    // Table and column names
    public static final String TABLE_STATES = "states";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STATE_NAME = "state_name";
    public static final String COLUMN_CAPITAL_NAME = "capital_name";

    // SQL to create the states table
    private static final String CREATE_TABLE_STATES = "CREATE TABLE " + TABLE_STATES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_STATE_NAME + " TEXT NOT NULL, " +
            COLUMN_CAPITAL_NAME + " TEXT NOT NULL" +
            ");";

    // Constructor
    public StatesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the states table
        db.execSQL(CREATE_TABLE_STATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATES);
        onCreate(db);
    }

    public String[] getStateAndCapitalById(int id) {
        if(id < 0){
            Log.d("getStatebyId", "invalid");
            return null;

        }
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get the state and capital by ID
        String query = "SELECT " + COLUMN_STATE_NAME + ", " + COLUMN_CAPITAL_NAME +
                " FROM " + TABLE_STATES +
                " WHERE " + COLUMN_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        String[] stateAndCapital = new String[2];

        // If a row is found, construct the result string
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String state = cursor.getString(cursor.getColumnIndex(COLUMN_STATE_NAME));
            @SuppressLint("Range") String capital = cursor.getString(cursor.getColumnIndex(COLUMN_CAPITAL_NAME));
            stateAndCapital[0] = state;
            stateAndCapital[1] = capital;
            cursor.close();
        }

        return stateAndCapital; // Return the result or null if not found
}
    public boolean isTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_STATES;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;  // If count is 0, the table is empty
    }


}



