package com.android.bakingfavorites.Steps_detail_fragments;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bakingfavorites.DetailFragments.StepsPojo;
import com.android.bakingfavorites.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExoPlayerFragment extends Fragment {
    private SimpleExoPlayer mExoPlayer;
    private long exoResumeTimeStamp;
    final String TAG = ExoPlayerFragment.class.getSimpleName();
    @Nullable
    @BindView(R.id.exoplayer_view)
    PlayerView mExoPlayerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_exoplayer, container, false );
        ButterKnife.bind(this, rootView);
        if (savedInstanceState == null) {
            exoResumeTimeStamp = 0;
        } else {
            exoResumeTimeStamp = savedInstanceState.getLong("exo_player_timestamp");
        }

        Bundle bundle = getArguments();
        StepsPojo singleStep = bundle.getParcelable("step");
        String videoURL = singleStep.getVideoURL();
        Log.e(TAG, "onCreateView: " + videoURL );
        if (mExoPlayer == null) {
            initializePlayer(videoURL);
        }

        return rootView;
    }

    private void initializePlayer (String recipeVideoUri)  {
        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mExoPlayerView.setPlayer(mExoPlayer);
            DefaultDataSourceFactory dataSourceFactory =
                    new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "RecipePlayer"));
            ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(recipeVideoUri));
            mExoPlayer.prepare(extractorMediaSource);
            Log.e(TAG, "initializePlayer: " + exoResumeTimeStamp );

            mExoPlayer.seekTo(exoResumeTimeStamp);

            mExoPlayer.setPlayWhenReady(true);
        }


    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            exoResumeTimeStamp = savedInstanceState.getLong("exo_player_timestamp");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mExoPlayer!= null) {
            releaseExoPlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        long timeStamp = mExoPlayer.getCurrentPosition();
        long exoPlayerEndTime = mExoPlayer.getDuration();
        Log.e(TAG, "onSaveInstanceState: " + timeStamp );
        Log.e(TAG, "onSaveInstanceState: " + exoPlayerEndTime );
        if (exoPlayerEndTime < timeStamp) {
            timeStamp = 0;
        }
        outState.putLong("exo_player_timestamp", timeStamp);
        Log.e(TAG, "onSaveInstanceState: " + timeStamp );
        super.onSaveInstanceState(outState);
    }

    private void releaseExoPlayer() {

        mExoPlayer.stop();
        mExoPlayer.release();

    }
}
