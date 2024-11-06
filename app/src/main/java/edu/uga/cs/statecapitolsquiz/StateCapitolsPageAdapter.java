package edu.uga.cs.statecapitolsquiz;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StateCapitolsPageAdapter extends FragmentStateAdapter {

    private int score = 0; // Current score

    public StateCapitolsPageAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Pass the score dynamically through the arguments of each fragment
        Log.d("StateCapitolsPageAdapter", "createFragment: + " + position + " " + score);
        return StateCapitolsQuizFragment.newInstance(position, score);
    }

    @Override
    public int getItemCount() {
        return 7; // 6 questions + result page
    }

    public void updateScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
