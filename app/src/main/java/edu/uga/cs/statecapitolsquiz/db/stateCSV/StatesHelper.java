package edu.uga.cs.statecapitolsquiz.db.stateCSV;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StatesHelper extends SQLiteOpenHelper {

    // Database version and name
    private static final int DATABASE_VERSION = 1;
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
}



