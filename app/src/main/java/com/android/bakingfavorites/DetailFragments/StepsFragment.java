package com.android.bakingfavorites.DetailFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bakingfavorites.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment {
    String TAG = StepsFragment.class.getSimpleName();
    private boolean TabletLayout;
    @Nullable
    @BindView(R.id.steps_recycler_view)
    RecyclerView stepsRecyclerView;

    public StepsFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        ArrayList<StepsPojo> stepsList = bundle.getParcelableArrayList("stepsKey");
        if (stepsList == null) {
            Log.e(TAG, "onCreateView: " + "steps list is null" );
        } else {
            Log.e(TAG, "onCreateView: " +stepsList.get(0).getDescription() );
        }

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StepsRecyclerViewAdapter stepsRecyclerViewAdapter = new StepsRecyclerViewAdapter(stepsList, getContext());
        stepsRecyclerView.setAdapter(stepsRecyclerViewAdapter);





        return rootView;

    }
}
