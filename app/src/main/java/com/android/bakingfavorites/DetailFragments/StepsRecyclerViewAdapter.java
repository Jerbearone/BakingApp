package com.android.bakingfavorites.DetailFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.bakingfavorites.R;
import com.android.bakingfavorites.StepsDetailActivity;
import com.android.bakingfavorites.Steps_detail_fragments.ExoPlayerFragment;

import java.util.ArrayList;

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<StepsPojo> mSteps = new ArrayList<>();
    private Context mContext;

    public StepsRecyclerViewAdapter(ArrayList<StepsPojo> steps, Context context) {
        this.mSteps = steps;
        this.mContext = context;
    }

    @NonNull
    @Override
    public StepsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_steps,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.stepDescription.setText(mSteps.get(i).getStepDescription());
        viewHolder.list_item_steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //this is where i can make fragments.
                StepsPojo singleStep = mSteps.get(i);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                boolean TabletLayout;
                if (v.getRootView().findViewById(R.id.tablet_detail_layout) != null) {
                    TabletLayout = true;
                } else {
                    TabletLayout = false;
                }

                if (TabletLayout == false) {
                    Log.e(StepsRecyclerViewAdapter.class.getSimpleName(), "onClick: " + "is false");

                    //this will happen if the layout is a phone layout

                    Intent selectDetailRecipe = new Intent(v.getContext(), StepsDetailActivity.class);
                    selectDetailRecipe.putExtra("singleStep", mSteps.get(i));
                    mContext.startActivity(selectDetailRecipe);
                } else {
                    TextView descriptionTextView = v.getRootView().findViewById(R.id.description_detail_view);
                    String description_text = singleStep.getStepDescription();
                    descriptionTextView.setText(description_text);

                    TextView detailsTextView = v.getRootView().findViewById(R.id.step_description_detail_View);
                    String step_detail_text = singleStep.getDescription();

                    if (step_detail_text.equals(description_text)) {
                        detailsTextView.setVisibility(View.INVISIBLE);
                    } else {
                        detailsTextView.setVisibility(View.VISIBLE);
                        detailsTextView.setText(step_detail_text);

                    }

                    if (singleStep.getVideoURL().equals("")) {
                        FrameLayout exoPlayerContainer = v.getRootView().findViewById(R.id.exoPlayer_container);
                        ExoPlayerFragment exoPlayerFragment;
                        exoPlayerFragment = new ExoPlayerFragment();
                        Bundle stepBundle = new Bundle();
                        stepBundle.putParcelable("step", singleStep);
                        exoPlayerFragment.setArguments(stepBundle);
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.exoPlayer_container, exoPlayerFragment)
                                .commit();
                        exoPlayerContainer.setVisibility(View.INVISIBLE);

                    } else {
                        FrameLayout exoPlayerContainer = v.getRootView().findViewById(R.id.exoPlayer_container);
                        exoPlayerContainer.setVisibility(View.VISIBLE);
                        Log.e(StepsRecyclerViewAdapter.class.getSimpleName(), "onClick: " + "is true");

                        ExoPlayerFragment exoPlayerFragment;
                        exoPlayerFragment = new ExoPlayerFragment();
                        Bundle stepBundle = new Bundle();
                        stepBundle.putParcelable("step", singleStep);
                        exoPlayerFragment.setArguments(stepBundle);
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.exoPlayer_container, exoPlayerFragment)
                                .commit();

                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stepDescription;
        ConstraintLayout list_item_steps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            list_item_steps = itemView.findViewById(R.id.steps_list_item_layout);
            stepDescription = itemView.findViewById(R.id.step_description);
        }
    }
}
