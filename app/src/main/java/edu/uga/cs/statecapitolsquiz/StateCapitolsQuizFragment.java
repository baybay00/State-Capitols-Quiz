package edu.uga.cs.statecapitolsquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class StateCapitolsQuizFragment extends Fragment {

    private int qNum = 0; // Question number to display
    private int score = 0; // Current score
    private ViewPager2 pager;
    private TextView question;

    public StateCapitolsQuizFragment() {
        // Required empty public constructor
    }

    public static StateCapitolsQuizFragment newInstance(int qNum, int score) {
        StateCapitolsQuizFragment fragment = new StateCapitolsQuizFragment();
        Bundle args = new Bundle();
        args.putInt("qNum", qNum);
        args.putInt("score", score);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            qNum = getArguments().getInt("qNum");
            score = getArguments().getInt("score");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_state_capitols_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager = getActivity().findViewById(R.id.pager);
        question = view.findViewById(R.id.question);
        RadioGroup rg = view.findViewById(R.id.rg);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selected = view.findViewById(checkedId);
            // Ensure pager and adapter are properly referenced
            if (pager != null && pager.getAdapter() instanceof StateCapitolsPageAdapter) {
                StateCapitolsPageAdapter adapter = (StateCapitolsPageAdapter) pager.getAdapter();

                // Check the answer and increment score if correct
                if (selected != null && selected.getText().equals("Option 1")) {
                    adapter.incrementScore();
                    Log.d("StateCapitolsQuizFragment", "Score updated: " + adapter.getScore());
                }

                if (pager.getCurrentItem() < adapter.getItemCount() - 1) {
                    pager.setUserInputEnabled(true);
                    pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                } else if (qNum == 6) { // result page
                    int finalScore = adapter.getScore();
                    question.setText("Final score: " + finalScore + "/6");
                    rg.setVisibility(View.GONE);
                }
            }
        });
        if (qNum == 6 && pager.getAdapter() instanceof StateCapitolsPageAdapter) {
            int finalScore = ((StateCapitolsPageAdapter) pager.getAdapter()).getScore();
            question = view.findViewById(R.id.question);
            question.setText("Final score: " + finalScore + "/6");
            rg.setVisibility(View.GONE);
        }
    }

    public void setFinalScore(int score)
    {
        if(qNum == 6)
        {
            if(question != null){
                question.setText("Final score: " + score + "/6");
            }
        }
    }
}