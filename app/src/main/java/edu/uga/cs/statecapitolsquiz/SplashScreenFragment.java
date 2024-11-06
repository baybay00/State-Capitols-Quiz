package edu.uga.cs.statecapitolsquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SplashScreenFragment extends Fragment {
    public SplashScreenFragment() {
        // Required empty public constructor
    }

    private Button playButton;
    private Button leaderboardButton;
    private Button helpButton;
    private Button pastQuizzesButton;
    private ViewPager2 pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        playButton = view.findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
        leaderboardButton = view.findViewById(R.id.leaderboard_button);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLeaderboard();
            }
        });
        helpButton = view.findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHelp();
            }
        });
        pastQuizzesButton = view.findViewById(R.id.past_quizzes_button);
        pastQuizzesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPastQuizzes();
            }
        });
        pager = view.findViewById(R.id.pager);
        return view;
    }

    private void startQuiz() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new StateCapitolsQuizFragment());
        transaction.addToBackStack(null);
        transaction.commit();
        StateCapitolsQuizFragment quizFragment = new StateCapitolsQuizFragment();
        quizFragment.setPager(pager);
        if (pager != null) {
            StateCapitolsPageAdapter adapter = new StateCapitolsPageAdapter(getParentFragmentManager(), getLifecycle());
            pager.setAdapter(adapter);
            pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
            pager.setOffscreenPageLimit(3);
        }
    }
    public void startLeaderboard() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new LeaderboardFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void startHelp() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HelpFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void startPastQuizzes() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new PastQuizzesFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public ViewPager2 getPager() {
        return pager;
    }
}