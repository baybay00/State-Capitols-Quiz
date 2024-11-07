package edu.uga.cs.statecapitolsquiz.task;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import edu.uga.cs.statecapitolsquiz.db.QuizHelper;

public class CreateQuizTableTask extends AsyncTask<Void, Void, Void> {

    private final Context context;

    public CreateQuizTableTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        QuizHelper quizHelper = QuizHelper.getInstance(context);
        SQLiteDatabase db = quizHelper.getWritableDatabase();

        return null; // No need to return anything
    }

    @Override
    protected void onPostExecute(Void result) {
        // Optionally update the UI or notify the user once the table is created
        Log.d("CreateQuizTableTask", "Table creation task completed.");
    }
}
