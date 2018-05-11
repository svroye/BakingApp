package com.example.steven.bakingapp.Utils;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by Steven on 10/05/2018.
 */

public class ExoPlayerUtils {

    // https://github.com/ayalus/ExoPlayer-2-Example/blob/master/ExoPlayer2Example/app/src/main/AndroidManifest.xml
    // https://android.jlelse.eu/android-exoplayer-starters-guide-6350433f256c
    public static SimpleExoPlayer initializePlayer(Context context, SimpleExoPlayerView playerView,
                                                   boolean playWhenReady, String videoUriString,
                                                   int currentWindow, long playbackPosition) {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        LoadControl loadControl = new DefaultLoadControl();
        DataSource.Factory mediaDataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "BakingApp"),
                (TransferListener<? super DataSource>) bandwidthMeter);

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);

        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        if (videoUriString != null) {
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUriString),
                    mediaDataSourceFactory, extractorsFactory, null, null);
            player.prepare(mediaSource);
        } else {
            playerView.setVisibility(View.GONE);
        }
        player.seekTo(currentWindow, playbackPosition);
        return player;
    }

    public static void releasePlayer(SimpleExoPlayer simpleExoPlayer){
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }


}
