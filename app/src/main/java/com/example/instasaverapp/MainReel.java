package com.example.instasaverapp;

public class MainReel {
    public MainReel(ModelClass shortcode_media) {
        this.shortcode_media = shortcode_media;
    }

    private ModelClass shortcode_media;

    public ModelClass getShortcode_media() {
        return shortcode_media;
    }

    public void setShortcode_media(ModelClass shortcode_media) {
        this.shortcode_media = shortcode_media;
    }
}
