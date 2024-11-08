package edu.uga.cs.statecapitolsquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.uga.cs.statecapitolsquiz.db.QuizHelper;
import edu.uga.cs.statecapitolsquiz.db.models.Quiz;
import edu.uga.cs.statecapitolsquiz.db.stateCSV.StatesHelper;
import edu.uga.cs.statecapitolsquiz.task.CreateQuizTableTask;
import edu.uga.cs.statecapitolsquiz.task.CreateQuizTask;
import edu.uga.cs.statecapitolsquiz.task.GrabStateByIdTask;
import edu.uga.cs.statecapitolsquiz.task.LoadCSVTask;
import edu.uga.cs.statecapitolsquiz.task.ShowAllQuizTask;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pager = findViewById(R.id.pager);
        pager.setVisibility(View.GONE);
        if(savedInstanceState == null)
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new SplashScreenFragment());
            transaction.commit();
        }
        //testing db creation
        StatesHelper statesHelper = new StatesHelper(this);
        QuizHelper quizHelper = new QuizHelper(this);
        if (statesHelper.isTableEmpty()) {
            new LoadCSVTask(this).execute();
        } else {
            Log.d("CSVDatabase", "States and capitals already exist in the database.");
        }

        //testing quiz
        if (quizHelper.isTableEmpty()){
            new CreateQuizTableTask(this).execute();
        } else {
            Log.d("CSVDatabase", "States and capitals already exist in the database.");
        }
        //did not know how to make the gets async
        List<Quiz> quizList = quizHelper.getAllQuizzes();

        for (Quiz e: quizList)
            Log.d("Tester", e.toString());
        }

    /**
     * starts the quiz
     */
    public void startQuiz()
    {
        StateCapitolsPageAdapter adapter = new StateCapitolsPageAdapter(getSupportFragmentManager(), getLifecycle());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(6);
        pager.setVisibility(View.VISIBLE);
        pager.setCurrentItem(0, false);
    }
}

