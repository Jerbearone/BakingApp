package com.android.bakingfavorites;

import android.content.Intent;
import android.content.res.Configuration;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.bakingfavorites.DetailFragments.StepsPojo;
import com.android.bakingfavorites.Steps_detail_fragments.ExoPlayerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsDetailActivity extends AppCompatActivity {
    final String exoplayerFragmentTag = "EXOTAG";
    ExoPlayerFragment exoPlayerFragment;
    @Nullable
    @BindView(R.id.description_detail_view)
    TextView descriptionTextView;

    @Nullable
    @BindView(R.id.step_description_detail_View)
    TextView detailsTextView;

    @Nullable
    @BindView(R.id.exoPlayer_container)
    FrameLayout exoPlayerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_detail);
        ButterKnife.bind(this);

        //up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent receivedStep = getIntent();
        StepsPojo singleStep = receivedStep.getParcelableExtra("singleStep");
        int screen_orientation = getResources().getConfiguration().orientation;
        if (screen_orientation == Configuration.ORIENTATION_PORTRAIT) {

            String description_text = singleStep.getStepDescription();
            descriptionTextView.setText(description_text);
            String step_detail_text = singleStep.getDescription();

            if (step_detail_text.equals(description_text)) {
                detailsTextView.setVisibility(View.INVISIBLE);
            } else {
                detailsTextView.setVisibility(View.VISIBLE);
                detailsTextView.setText(step_detail_text);

            }
        }
        if(singleStep.getVideoURL().equals("")) {
            exoPlayerContainer.setVisibility(View.INVISIBLE);
        } else {

            //handle saving instance of fragment

            if (savedInstanceState == null) {
                exoPlayerFragment = new ExoPlayerFragment();
                Bundle stepBundle = new Bundle();
                stepBundle.putParcelable("step", singleStep);
                exoPlayerFragment.setArguments(stepBundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.exoPlayer_container, exoPlayerFragment)
                        .commit();
            } else {
                exoPlayerFragment = (ExoPlayerFragment) getSupportFragmentManager()
                        .findFragmentByTag(exoplayerFragmentTag);
            }

        }



    }
}
