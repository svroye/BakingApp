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

    public ExoPlayerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_exoplayer, container, false);

        if (getArguments() != null){
            recipeStep = getArguments().getParcelable(getString(R.string.single_step_key));
        }

        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            playbackPosition = savedInstanceState.getLong("playbackPosition");
            currentWindow = savedInstanceState.getInt("currentWindow");
        }

        simpleExoPlayerView = rootView.findViewById(R.id.fragmentExoplayer_moviePlayer);
        simpleExoPlayer = ExoPlayerUtils.initializePlayer(getContext(), simpleExoPlayerView,
                playWhenReady, recipeStep.getUrlToVideo(), currentWindow, playbackPosition);

        setInitialOrientation();
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
    }

    private void setInitialOrientation(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI();
        } else {
        }
    }

    private void hideSystemUI(){
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}
