package edu.uga.cs.statecapitolsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StateCapitolsQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StateCapitolsQuizFragment extends Fragment {

    private int qNum; //which question number to display
    private ViewPager2 pager;

    public StateCapitolsQuizFragment() {
        // Required empty public constructor
    }

    public static StateCapitolsQuizFragment newInstance(int qNum) {
        StateCapitolsQuizFragment fragment = new StateCapitolsQuizFragment();
        Bundle args = new Bundle();
        args.putInt("qNum", qNum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            qNum = getArguments().getInt("qNum");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state_capitols_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        pager = view.findViewById(R.id.viewpager);
        StateCapitolsPageAdapter adapter = new StateCapitolsPageAdapter(getChildFragmentManager(),getViewLifecycleOwner().getLifecycle());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(adapter);

        TextView question = view.findViewById(R.id.question);
        Button answer1 = view.findViewById(R.id.answer1);
        Button answer2 = view.findViewById(R.id.answer2);
        Button answer3 = view.findViewById(R.id.answer3);
    }
}