package edu.uga.cs.statecapitolsquiz.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import edu.uga.cs.statecapitolsquiz.db.stateCSV.StatesHelper;

public class GrabStateByIdTask extends AsyncTask<Integer, Void, String[]> {

    private final Context context;
    private int id;

    // Constructor to pass the context and ID
    public GrabStateByIdTask(Context context, int id) {
        this.context = context;
        this.id = id;
    }

    // doInBackground receives the ID and performs the database query
    @Override
    protected String[] doInBackground(Integer... params) {
        if (params.length > 0) {
            id = params[0]; // Get the ID from the params array
        }

        StatesHelper statesHelper = new StatesHelper(context);
        return statesHelper.getStateAndCapitalById(id); // Assuming this returns a String[] of state and capital
    }

    // onPostExecute receives the result from doInBackground and executes on the main thread
    @Override
    protected void onPostExecute(String[] result) {
        if (result != null && result.length == 2) {
            String state = result[0];
            String capital = result[1];
            Toast.makeText(context, "State: " + state + ", Capital: " + capital, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "No state found with the given ID", Toast.LENGTH_SHORT).show();
        }
    }
}


