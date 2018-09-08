package com.weeturretstudio.warbeleth.android.popularmovies.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieVideo;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MovieVideoConverter {

    @TypeConverter
    public String fromMovieVideoList(ArrayList<MovieVideo> movieVideos) {
        if(movieVideos == null)
            return null;

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MovieVideo>>(){}.getType();

        String json = gson.toJson(movieVideos, type);
        return json;
    }

    @TypeConverter
    public ArrayList<MovieVideo> toMovieVideoList(String movieVideos) {
        if(movieVideos == null)
            return null;

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MovieVideo>>(){}.getType();

        ArrayList<MovieVideo> movieVideoList = gson.fromJson(movieVideos, type);
        return movieVideoList;
    }
}
