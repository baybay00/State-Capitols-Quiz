package edu.uga.cs.statecapitolsquiz.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.statecapitolsquiz.db.models.Quiz;

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

    public QuizHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table with updated column names
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

    // Insert a new quiz
    public long insertQuiz(Quiz quiz) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, quiz.getDate());
        values.put(COLUMN_TIME, quiz.getTime());
        values.put(COLUMN_RESULT, quiz.getResult());

        long id = db.insert(TABLE_QUIZZES, null, values);
        db.close();
        return id;
    }

    // Get all quizzes
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //should grab from newest
        String query = "SELECT * FROM " + TABLE_QUIZZES + " ORDER BY " + COLUMN_ID+ " DESC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                @SuppressLint("Range") int result = cursor.getInt(cursor.getColumnIndex(COLUMN_RESULT));

                Quiz quiz = new Quiz(id, date, time, result);
                quizList.add(quiz);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return quizList;
    }

    public List<Quiz> getHighScores() {
        List<Quiz> quizList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_QUIZZES + " ORDER BY " + COLUMN_RESULT + " DESC LIMIT 10";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                @SuppressLint("Range") int result = cursor.getInt(cursor.getColumnIndex(COLUMN_RESULT));

                Quiz quiz = new Quiz(id, date, time, result);
                quizList.add(quiz);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return quizList;
    }

    // Get a quiz by ID
    //overhead in case you need it
    public Quiz getQuizById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_QUIZZES + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                @SuppressLint("Range") int result = cursor.getInt(cursor.getColumnIndex(COLUMN_RESULT));

                cursor.close();
                return new Quiz(id, date, time, result);
            }



        cursor.close();
        return null; // Return null if no quiz with the given ID exists
    }

    //check if exist
    public boolean isTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_QUIZZES;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;  // If count is 0, the table is empty
    }
}




