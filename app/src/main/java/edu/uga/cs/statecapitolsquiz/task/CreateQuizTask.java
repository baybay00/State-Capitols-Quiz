package edu.uga.cs.statecapitolsquiz.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import edu.uga.cs.statecapitolsquiz.db.QuizHelper;
import edu.uga.cs.statecapitolsquiz.db.models.Quiz;

public class CreateQuizTask extends AsyncTask<Quiz, Void, Long> {

    private final Context context;
    Quiz quiz;

    public CreateQuizTask(Context context, Quiz quiz) {
        this.context = context;
        this.quiz = quiz;
    }

    @Override
    protected Long doInBackground(Quiz... quizzes) {

        // Get the database instance
        QuizHelper quizHelper = QuizHelper.getInstance(context);
        long result = -1;  // Default to -1 for failure

        // Insert quiz into the database
        try {
            result = quizHelper.insertQuiz(quiz);  // Call the method to create quiz
        } catch (Exception e) {
            Log.e("CreateQuizTask", "Error inserting quiz", e);
        }

        return result;  // Return the result (ID of the inserted quiz)
    }

    @Override
    protected void onPostExecute(Long result) {
        // This method runs on the main UI thread after doInBackground finishes.
        if (result != -1) {
            Toast.makeText(context, "Quiz created successfully with ID: " + result, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error creating quiz", Toast.LENGTH_SHORT).show();
        }
    }
}

