package edu.uga.cs.statecapitolsquiz.db.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import edu.uga.cs.statecapitolsquiz.db.QuizHelper;
import edu.uga.cs.statecapitolsquiz.db.models.Quiz;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApiClient {

    private static final String BASE_URL = "http://uml.cs.uga:8080/stateCapitolQuiz/rest/";
    private static final String DEBUG_TAG = "StateCapitolQuizRest";

    private SQLiteDatabase db;
    private QuizHelper quizHelper;
    private static Retrofit retrofit;
    private static QuizService quizService;

    // Constructor with context to initialize database helper
    public ApiClient(Context context) {
        this.quizHelper = QuizHelper.getInstance(context);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        quizService = retrofit.create(QuizService.class);
    }

    // Open database connection
    public void open() {
        db = quizHelper.getWritableDatabase();
    }

    // Close database connection
    public void close() {
        if (quizHelper != null) {
            quizHelper.close();
        }
    }


    //-------------SHOULD BE OTHER CLASS BUT IS SHORT---------------------------------//
    // Get all quizzes
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Query the database to retrieve all quizzes
            cursor = db.query(
                    QuizHelper.TABLE_QUIZZES,
                    null,null,null,null,null,null
            );

            // Loop through all rows in the result set
            while (cursor.moveToNext()) {
                // Extract data from the cursor for each column
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(QuizHelper.COLUMN_ID));
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow(QuizHelper.COLUMN_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(QuizHelper.COLUMN_TIME));
                int result = cursor.getInt(cursor.getColumnIndexOrThrow(QuizHelper.COLUMN_RESULT));


                // Create a new Quiz object and add it to the list
                Quiz quiz = new Quiz(id, dateStr, time, result);
                quizzes.add(quiz);
            }
        } finally {
            // Close the cursor to free up resources
            if (cursor != null) {
                cursor.close();
            }
        }

        return quizzes;
    }

    // Get a specific quiz by ID
    public Quiz getQuizById(long id) {
        Call<Quiz> call = quizService.getQuizById(id);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(DEBUG_TAG, "Error retrieving quiz by ID", e);
            return null;
        }
    }

    // Create a new quiz
    public Quiz createQuiz(Quiz quiz) {
        // Prepare values for SQLite insert
        ContentValues values = new ContentValues();
        values.put(QuizHelper.COLUMN_DATE, quiz.getDate().toString());  // Format date as needed
        values.put(QuizHelper.COLUMN_TIME, quiz.getTime());
        values.put(QuizHelper.COLUMN_RESULT, quiz.getResult());

        // Insert into SQLite database
        long id = db.insert(QuizHelper.TABLE_QUIZZES, null, values);
        if (id == -1) {
            Log.e(DEBUG_TAG, "Error inserting quiz into SQLite database");
            return null;
        }

        // Set the SQLite-generated ID in the Quiz object
        quiz.setId(id);

        Call<Quiz> call = quizService.createQuiz(quiz);
        try {
            Quiz createdQuiz = call.execute().body();
            if (createdQuiz != null) {
                Log.d(DEBUG_TAG, "Quiz successfully created on the server with ID: " + createdQuiz.getId());
                return createdQuiz;
            } else {
                Log.e(DEBUG_TAG, "Error creating quiz on server, response body is null");
                return null;
            }
        } catch (IOException e) {
            Log.e(DEBUG_TAG, "Error creating quiz on server", e);
            return null;
        }
    }
}

