package edu.uga.cs.statecapitolsquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class StateCapitolsQuizFragment extends Fragment {

    private int qNum = 0; // Question number to display
    private int score = 0; // Current score
    private ViewPager2 pager;

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

    public void setPager(ViewPager2 pager)
    {
        this.pager = pager;
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

        // RadioGroup listener for answer selection
        RadioGroup rg = view.findViewById(R.id.rg);
        rg.setOnCheckedChangeListener((group, checkedId) -> { //will fix this to track only when page is changed to ensure correct score
            RadioButton selected = view.findViewById(checkedId);
            if (selected.getText().equals("Option 1")) {
                score++; // Increase score when correct answer is selected
            }

            ViewPager2 pager = getActivity().findViewById(R.id.pager);
            if (pager != null && pager.getAdapter() != null) {
                pager.setCurrentItem(checkedId);
                StateCapitolsPageAdapter adapter = (StateCapitolsPageAdapter) pager.getAdapter();
                adapter.updateScore(score);  // Update the score in the adapter
            }
        });
    }
}