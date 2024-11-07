package edu.uga.cs.statecapitolsquiz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuizHelper extends SQLiteOpenHelper {

    // Constants for table and column names
    public static final String DATABASE_NAME = "quizDatabase.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_QUIZZES = "quizzes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_RESULT = "result";

    private static QuizHelper instance;

    public static synchronized QuizHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizHelper(context.getApplicationContext());
        }
        return instance;
    }

    private QuizHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUIZ_TABLE = "CREATE TABLE " + TABLE_QUIZZES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIME + " TEXT,"
                + COLUMN_RESULT + " INTEGER" + ")";
        db.execSQL(CREATE_QUIZ_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        onCreate(db);
    }


}




