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
    private StateCapitolsQuizFragment lastFragment;

    public StateCapitolsPageAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        StateCapitolsQuizFragment fragment = StateCapitolsQuizFragment.newInstance(position, score);
        if(position == getItemCount() - 1) {
            lastFragment = fragment;
            return lastFragment;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 7; // 6 questions + result page
    }

    public void incrementScore() {
        score++;
        if(lastFragment != null) {
            lastFragment.setFinalScore(score);
        }
    }

    public int getScore() {
        return score;
    }

}
