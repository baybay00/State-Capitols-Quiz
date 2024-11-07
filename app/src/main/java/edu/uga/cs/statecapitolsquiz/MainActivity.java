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

import edu.uga.cs.statecapitolsquiz.db.stateCSV.StatesHelper;
import edu.uga.cs.statecapitolsquiz.task.grabStateByIdTask;
import edu.uga.cs.statecapitolsquiz.task.loadCSVTask;

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
        if (statesHelper.isTableEmpty()) {
            new loadCSVTask(this).execute();
        } else {
            Log.d("CSVDatabase", "States and capitals already exist in the database.");

        }

        //grab id
        new grabStateByIdTask(this, 51).execute();


    }

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

