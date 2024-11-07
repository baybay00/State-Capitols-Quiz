package edu.uga.cs.statecapitolsquiz.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.uga.cs.statecapitolsquiz.db.stateCSV.StatesHelper;

public class loadCSVTask extends AsyncTask<Void, Void> {

    private final Context context;

    public loadCSVTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        StatesHelper statesHelper = new StatesHelper(context);
        SQLiteDatabase db = statesHelper.getWritableDatabase();

        try (InputStream is = context.getAssets().open("state_capitals.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            db.beginTransaction();
            try {
                while ((line = reader.readLine()) != null) {
                    String[] stateCapital = line.split(",");
                        String state = stateCapital[0];
                        String capital = stateCapital[1];

                        ContentValues values = new ContentValues();
                        values.put(StatesHelper.COLUMN_STATE_NAME, state);
                        values.put(StatesHelper.COLUMN_CAPITAL_NAME, capital);

                        db.insert(StatesHelper.TABLE_STATES, null, values);

                    Log.d("loadCSVTask", "loading " + state + " " + capital);
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // This method runs on the main UI thread after doInBackground finishes.
        // You can update the UI here, such as showing a message to the user.
        Toast.makeText(context, "States and capitals added to database", Toast.LENGTH_SHORT).show();
    }
}

