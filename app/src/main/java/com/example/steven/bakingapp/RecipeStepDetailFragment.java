package com.example.steven.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.bakingapp.Objects.RecipeStep;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;


/**
 * Created by Steven on 30/04/2018.
 */

public class RecipeStepDetailFragment extends Fragment {

    private RecipeStep recipeStep;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;

    private boolean playWhenReady = true;

    public RecipeStepDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);

        if (getArguments() != null) {
            recipeStep = getArguments().getParcelable(getString(R.string.single_step_key));
        }

        simpleExoPlayerView = rootView.findViewById(R.id.recipeSteps_moviePlayer);
        initializePlayer();
        TextView stepDescription = rootView.findViewById(R.id.recipeSteps_stepDescription);
        stepDescription.append(recipeStep.getFullDescription());

        if (savedInstanceState != null) {
            simpleExoPlayer.seekTo(savedInstanceState.getLong(getContext().getString(R.string.current_time_exoplayer_key)));
        }

        return rootView;

    }

    // https://github.com/ayalus/ExoPlayer-2-Example/blob/master/ExoPlayer2Example/app/src/main/AndroidManifest.xml
    // https://android.jlelse.eu/android-exoplayer-starters-guide-6350433f256c
    public void initializePlayer() {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        LoadControl loadControl = new DefaultLoadControl();
        DataSource.Factory mediaDataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "BakingApp"),
                                    (TransferListener<? super DataSource>) bandwidthMeter);

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);

        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setPlayWhenReady(playWhenReady);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        String videoUriString = recipeStep.getUrlToVideo();
        if (videoUriString != null) {
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUriString),
                    mediaDataSourceFactory, extractorsFactory, null, null);
            simpleExoPlayer.prepare(mediaSource);
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(getContext().getString(R.string.current_time_exoplayer_key),
                simpleExoPlayer.getCurrentPosition());
        releasePlayer();
    }

    private void releasePlayer(){
        simpleExoPlayer.release();
    }
}
