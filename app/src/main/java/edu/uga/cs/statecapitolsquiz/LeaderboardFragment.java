package edu.uga.cs.statecapitolsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import edu.uga.cs.statecapitolsquiz.db.QuizHelper;
import edu.uga.cs.statecapitolsquiz.db.models.Quiz;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    public static LeaderboardFragment newInstance() {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        // Reference to TableLayout
        TableLayout leaderboard = view.findViewById(R.id.leaderboard);

        // Fetch quiz data
        QuizHelper quizHelper = new QuizHelper(getContext());
        List<Quiz> quizzes = quizHelper.getHighScores();

        // Check if quizzes are available
        if (quizzes != null && !quizzes.isEmpty()) {
            for (Quiz quiz : quizzes) {
                TableRow tableRow = new TableRow(getActivity());

                // Create Game ID column
                TextView id = new TextView(getActivity());
                id.setText(String.valueOf(quiz.getId())); // Quiz ID
                id.setPadding(8, 8, 8, 8);
                id.setGravity(View.TEXT_ALIGNMENT_CENTER);
                id.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                tableRow.addView(id);

                // Create Score column
                TextView score = new TextView(getActivity());
                score.setText(String.valueOf(quiz.getResult())); // Quiz result
                score.setPadding(8, 8, 8, 8);
                score.setGravity(View.TEXT_ALIGNMENT_CENTER);
                score.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                tableRow.addView(score);

                // Create Date column
                TextView date = new TextView(getActivity());
                date.setText(quiz.getDate()); // Quiz date
                date.setPadding(8, 8, 8, 8);
                date.setGravity(View.TEXT_ALIGNMENT_CENTER);
                date.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                tableRow.addView(date);

                // Add the row to the TableLayout
                leaderboard.addView(tableRow);
            }
        } else {
            // Handle case where there are no past quizzes
            TextView noData = new TextView(getActivity());
            noData.setText("No past quizzes available.");
            noData.setGravity(View.TEXT_ALIGNMENT_CENTER);
            leaderboard.addView(noData);
        }

        return view;
    }
}
