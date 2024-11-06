package edu.uga.cs.statecapitolsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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

        TableLayout leaderboard = view.findViewById(R.id.leaderboard);

        String[][] mockData = {
                {"1", "6", "2024-11-06"},
                {"2", "6", "2024-11-05"},
                {"3", "6", "2024-11-04"},
        };

        for (String[] row : mockData) {
            TableRow tableRow = new TableRow(getActivity());

            // Game ID column
            TextView id = new TextView(getActivity());
            id.setText(row[0]);
            id.setPadding(8, 8, 8, 8);
            id.setGravity(View.TEXT_ALIGNMENT_CENTER);
            id.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tableRow.addView(id);

            // Score column
            TextView score = new TextView(getActivity());
            score.setText(row[1]);
            score.setPadding(8, 8, 8, 8);
            score.setGravity(View.TEXT_ALIGNMENT_CENTER);
            score.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tableRow.addView(score);

            // Date column
            TextView date = new TextView(getActivity());
            date.setText(row[2]);
            date.setPadding(8, 8, 8, 8);
            date.setGravity(View.TEXT_ALIGNMENT_CENTER);
            date.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tableRow.addView(date);

            leaderboard.addView(tableRow);
        }

        return view;
    }
}
