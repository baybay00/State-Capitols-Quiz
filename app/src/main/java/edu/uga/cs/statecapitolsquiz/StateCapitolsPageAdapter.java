package edu.uga.cs.statecapitolsquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StateCapitolsPageAdapter extends FragmentStateAdapter
{
    public StateCapitolsPageAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return StateCapitolsQuizFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
