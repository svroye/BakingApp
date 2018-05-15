package com.example.steven.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.steven.bakingapp.Objects.RecipeStep;
import com.example.steven.bakingapp.Utils.ExoPlayerUtils;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.squareup.picasso.Picasso;


/**
 * Fragment for displaying a videoplayer. Makes use of the ExoPlayer external library
 */

public class ExoPlayerFragment extends Fragment {

    // RecipeStep handled over to the fragment
    private RecipeStep recipeStep;

    // View and Player for the video
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private ImageView imageView;

    // values for the video to keep track when the screen rotates
    private boolean playWhenReady = false;
    private long playbackPosition = 0;
    private int currentWindow = 0;

    // value for checking tablet or phone
    private boolean isTablet;
    private boolean isVideo;

    /**
     * Mandatory empty constructor
     */
    public ExoPlayerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_exoplayer_image, container, false);

        // get the RecipeStep and boolean value from the Bundle added when the fragment was created
        if (getArguments() != null){
            recipeStep = getArguments().getParcelable(getString(R.string.single_step_key));
            isTablet = getArguments().getBoolean("isTablet");
        }

        // check for saved bundle; if so, take the values from the bundle and store them in the variables
        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            playbackPosition = savedInstanceState.getLong("playbackPosition");
            currentWindow = savedInstanceState.getInt("currentWindow");
            isTablet = savedInstanceState.getBoolean("isTablet");
        }

        // initialize the player and set the orientation for it
        simpleExoPlayerView = rootView.findViewById(R.id.fragmentExoplayer_moviePlayer);
        imageView = rootView.findViewById(R.id.fragmentExoplayer_image);

        String videoUrl = recipeStep.getVideoUrl();
        String thumbnailUrl = recipeStep.getThumbnailUrl();

        if (!videoUrl.equals("")){
            isVideo = true;
            imageView.setVisibility(View.GONE);

        } else if (!thumbnailUrl.equals("")){
            isVideo = false;
            simpleExoPlayerView.setVisibility(View.GONE);
            if (!thumbnailUrl.endsWith(".mp4")) {
                Picasso.get()
                        .load(recipeStep.getThumbnailUrl())
                        .fit()
                        .into(imageView);
                } else {
                imageView.setVisibility(View.GONE);
            }
        } else {
            isVideo = false;
            imageView.setVisibility(View.GONE);
            simpleExoPlayerView.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isVideo) ExoPlayerUtils.releasePlayer(simpleExoPlayer);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isVideo) ExoPlayerUtils.releasePlayer(simpleExoPlayer);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isVideo) {
            simpleExoPlayer = ExoPlayerUtils.initializePlayer(getContext(), simpleExoPlayerView,
                    playWhenReady, recipeStep.getVideoUrl(), currentWindow, playbackPosition);
            ExoPlayerUtils.setInitialOrientation(getContext(), getActivity(), isTablet);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // save the variables for handling screen rotation
        super.onSaveInstanceState(outState);
        if (isVideo) {
            outState.putLong("playbackPosition", simpleExoPlayer.getCurrentPosition());
            outState.putInt("currentWindow", simpleExoPlayer.getCurrentWindowIndex());
            outState.putBoolean("playWhenReady", simpleExoPlayer.getPlayWhenReady());
            outState.putBoolean("isTablet", isTablet);
        }
    }

}
