package edu.uga.cs.statecapitolsquiz.task;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.statecapitolsquiz.db.QuizHelper;
import edu.uga.cs.statecapitolsquiz.db.models.Quiz;

public class ShowAllQuizTask extends AsyncTask<Void, Void, List<Quiz>> {

    private final Context context;

    // Constructor accepts the context and a listener to handle the result
    public ShowAllQuizTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Quiz> doInBackground(Void... voids) {
        QuizHelper quizHelper = QuizHelper.getInstance(context);
        List<Quiz> quizList = quizHelper.getAllQuizzes();
        return quizList;  // Return the list of quizzes
    }

    @Override
    protected void onPostExecute(List<Quiz> quizzes) {
        super.onPostExecute(quizzes);

        if (quizzes != null && !quizzes.isEmpty()) {
            // If quizzes are found, pass them to the listener
            Toast.makeText(context, "quizzes found", Toast.LENGTH_SHORT).show();
        } else {
            // If no quizzes were found, show a message
            Toast.makeText(context, "No quizzes found", Toast.LENGTH_SHORT).show();
        }
    }

}

