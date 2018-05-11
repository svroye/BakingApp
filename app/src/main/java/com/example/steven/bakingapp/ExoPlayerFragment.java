package com.example.steven.bakingapp;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.steven.bakingapp.Objects.RecipeStep;
import com.example.steven.bakingapp.Utils.ExoPlayerUtils;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;


/**
 * Created by Steven on 10/05/2018.
 */

public class ExoPlayerFragment extends Fragment {

    private RecipeStep recipeStep;

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;

    private boolean playWhenReady = false;
    private long playbackPosition = 0;
    private int currentWindow = 0;
    private boolean isTablet;

    public ExoPlayerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_exoplayer, container, false);

        if (getArguments() != null){
            recipeStep = getArguments().getParcelable(getString(R.string.single_step_key));
            isTablet = getArguments().getBoolean("isTablet");
        }

        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            playbackPosition = savedInstanceState.getLong("playbackPosition");
            currentWindow = savedInstanceState.getInt("currentWindow");
            isTablet = savedInstanceState.getBoolean("isTablet");
        }

        simpleExoPlayerView = rootView.findViewById(R.id.fragmentExoplayer_moviePlayer);
        simpleExoPlayer = ExoPlayerUtils.initializePlayer(getContext(), simpleExoPlayerView,
                playWhenReady, recipeStep.getUrlToVideo(), currentWindow, playbackPosition);
        ExoPlayerUtils.setInitialOrientation(getContext(), getActivity(), isTablet);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!getActivity().isChangingConfigurations()) {
            simpleExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        simpleExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ExoPlayerUtils.releasePlayer(simpleExoPlayer);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("playbackPosition", simpleExoPlayer.getCurrentPosition());
        outState.putInt("currentWindow", simpleExoPlayer.getCurrentWindowIndex());
        outState.putBoolean("playWhenReady", simpleExoPlayer.getPlayWhenReady());
        outState.putBoolean("isTablet", isTablet);
    }

}
