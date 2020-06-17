package com.soundhive.core.tracks;

import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;

public class Track {

    private final String title;

    private final String description;

    private final String genre;

    //TODO add artwork

    public Track(JSONObject jsonTrack) {
        this.title = jsonTrack.getString("title");
        this.description = jsonTrack.getString("description");
        this.genre = jsonTrack.getString("genre");
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }
}