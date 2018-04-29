package com.example.steven.bakingapp.Objects;

/**
 * Created by Steven on 29/04/2018.
 */

public class RecipeStep {

    private String shortDescription;
    private String fullDescription;
    private String videoUrl;
    private String thumbnailUrl;

    public RecipeStep(String shortDescription, String fullDescription, String videoUrl, String thumbnailUrl) {
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
