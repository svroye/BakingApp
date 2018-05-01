package com.example.steven.bakingapp.Fragments;

import android.content.Context;
import android.media.MediaDataSource;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.bakingapp.Objects.RecipeStep;
import com.example.steven.bakingapp.R;
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

public class RecipeStepFragment extends Fragment {

    private RecipeStep recipeStep;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;

    public RecipeStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        if (getArguments() != null) {
            recipeStep = getArguments().getParcelable(getString(R.string.single_step_key));
        }

        simpleExoPlayerView = rootView.findViewById(R.id.recipeSteps_moviePlayer);
        initializePlayer();
        TextView stepDescription = rootView.findViewById(R.id.recipeSteps_stepDescription);
        stepDescription.append(recipeStep.getFullDescription());
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

        simpleExoPlayer.setPlayWhenReady(true);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        Uri uri = Uri.parse(recipeStep.getVideoUrl());

        MediaSource mediaSource = null;
        Log.d("AAAAAAAAAAAAAAAAA", (recipeStep.getVideoUrl().length() != 0) + "");
        Log.d("AAAAAAAAAAAAAAAAA", (recipeStep.getThumbnailUrl().length() != 0) + "");
        if (uri != null) {
            mediaSource = new ExtractorMediaSource(uri,
                    mediaDataSourceFactory, extractorsFactory, null, null);
            simpleExoPlayer.prepare(mediaSource);
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }
}
