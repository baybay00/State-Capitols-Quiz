package edu.uga.cs.statecapitolsquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.uga.cs.statecapitolsquiz.db.models.Quiz;
import edu.uga.cs.statecapitolsquiz.db.stateCSV.StatesHelper;
import edu.uga.cs.statecapitolsquiz.task.CreateQuizTask;

public class StateCapitolsQuizFragment extends Fragment {

    private int qNum = 0; // Question number to display
    private int score = 0; // Current score
    private ViewPager2 pager;
    private TextView question;
    private Button mainMenu;
    private Button leaderboard;
    private Button pastQuizzes;
    private Button option1;
    private Button option2;
    private Button option3;
    private String state;
    private String correctAnswer;
    private RadioGroup rg;

    public StateCapitolsQuizFragment() {
        // Required empty public constructor
    }

    public static StateCapitolsQuizFragment newInstance(int qNum, int score) {
        StateCapitolsQuizFragment fragment = new StateCapitolsQuizFragment();
        Bundle args = new Bundle();
        args.putInt("qNum", qNum);
        args.putInt("score", score);
        fragment.setArguments(args);
        Log.d("StateCapitolsQuizFragment", "newInstance called with qNum: " + qNum + ", score: " + score);
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

        // Initialize UI elements
        try {
            pager = getActivity().findViewById(R.id.pager);
            if (pager == null) {
                Log.e("StateCapitolsQuizFragment", "ViewPager2 pager is null!");
            } else if (!(pager.getAdapter() instanceof StateCapitolsPageAdapter)) {
                Log.e("StateCapitolsQuizFragment", "Pager adapter is not of type StateCapitolsPageAdapter!");
            }

            question = view.findViewById(R.id.question);
            rg = view.findViewById(R.id.rg);
            mainMenu = view.findViewById(R.id.menu);
            leaderboard = view.findViewById(R.id.resultleaderboard);
            pastQuizzes = view.findViewById(R.id.resultpastquizzes);
            option1 = view.findViewById(R.id.rb1);
            option2 = view.findViewById(R.id.rb2);
            option3 = view.findViewById(R.id.rb3);

            mainMenu.setVisibility(View.GONE);
            leaderboard.setVisibility(View.GONE);
            pastQuizzes.setVisibility(View.GONE);

            loadQuestionData();

            // Handle answer selection
            rg.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton selected = view.findViewById(checkedId);
                // Move to the next question if available
                if (pager != null && pager.getAdapter() instanceof StateCapitolsPageAdapter) {
                    StateCapitolsPageAdapter adapter = (StateCapitolsPageAdapter) pager.getAdapter();
                    if (selected != null && adapter != null) {
                        // If the selected answer is correct, increment score
                        if (selected.getText().equals(correctAnswer)) {
                            adapter.incrementScore(); // Increment score for correct answer
                        }
                    }
                    if (qNum < adapter.getItemCount() - 1) {
                        qNum++;
                        pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                    } else {
                        displayFinalScore();
                    }
                } else {
                    Log.e("StateCapitolsQuizFragment", "Pager or pager adapter is null!");
                }
            });
        } catch (Exception e) {
            Log.e("StateCapitolsQuizFragment", "Error initializing views or setting listeners", e);
        }
    }


    private void displayFinalScore() {
        if (qNum == 6) {
            int finalScore = ((StateCapitolsPageAdapter) pager.getAdapter()).getScore();
            rg.setVisibility(View.GONE);
            question.setText("Final score: " + finalScore + "/6");
            mainMenu.setOnClickListener(v -> {navigate(new SplashScreenFragment()); hideScoreScreen();});
            leaderboard.setOnClickListener(v -> {navigate(new LeaderboardFragment()); hideScoreScreen();});
            pastQuizzes.setOnClickListener(v -> {navigate(new PastQuizzesFragment()); hideScoreScreen();});
            mainMenu.setVisibility(View.VISIBLE);
            leaderboard.setVisibility(View.VISIBLE);
            pastQuizzes.setVisibility(View.VISIBLE);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd");
            String formattedDate = dateFormat.format(calendar.getTime());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String formattedTime = timeFormat.format(calendar.getTime());

            Quiz quiz = new Quiz(formattedDate, formattedTime, finalScore);

            //put quiz objext into the method
            new CreateQuizTask(getActivity(), quiz).execute();
        }
    }

    public void hideScoreScreen()
    {
        pager.setVisibility(View.GONE);
        question.setVisibility(View.GONE);
        mainMenu.setVisibility(View.GONE);
        leaderboard.setVisibility(View.GONE);
        pastQuizzes.setVisibility(View.GONE);
    }

    public void navigate(Fragment fragment)
    {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    public void loadQuestionData() {
        Random random = new Random();
        int num = random.nextInt(50) + 1;
        StatesHelper dbHelper = new StatesHelper(getContext());
        String[] stateAndCapital = dbHelper.getStateAndCapitalById(num);

        if (stateAndCapital != null && stateAndCapital.length == 2) {
            state = stateAndCapital[0];
            correctAnswer = stateAndCapital[1];

            question.setText("What is the capital of: " + state + "?");

            // Fetch random incorrect answers
            String[] incorrectAnswers = dbHelper.getRandomIncorrectCapitals(correctAnswer);

            // Shuffle answers
            List<String> answers = new ArrayList<>();
            answers.add(correctAnswer);
            answers.add(incorrectAnswers[0]);
            answers.add(incorrectAnswers[1]);

            Collections.shuffle(answers);

            // Ensure that answers are correctly set
            option1.setText(answers.get(0));
            option2.setText(answers.get(1));
            option3.setText(answers.get(2));
        } else {
            Log.e("StateCapitolsQuizFragment", "Error: State and capital data is null or incomplete");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // Get current score from the adapter
        int savedScore = ((StateCapitolsPageAdapter) pager.getAdapter()).getScore();
        int savedQNum = qNum;

        // Save the state to SharedPreferences
        SharedPreferences sharedPref = getActivity().getSharedPreferences("quiz_state", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("score", savedScore);
        editor.putInt("qNum", savedQNum);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Retrieve saved state from SharedPreferences
        SharedPreferences sharedPref = getActivity().getSharedPreferences("quiz_state", Context.MODE_PRIVATE);
        int restoredScore = sharedPref.getInt("score", 0);  // Default to 0 if not found
        int restoredQNum = sharedPref.getInt("qNum", 0);    // Default to 0 if not found

        // Set the restored score and question number
        ((StateCapitolsPageAdapter) pager.getAdapter()).setScore(restoredScore);
        qNum = restoredQNum;
    }

}