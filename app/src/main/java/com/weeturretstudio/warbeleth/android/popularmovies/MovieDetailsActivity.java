package com.weeturretstudio.warbeleth.android.popularmovies;

import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intentThatSpawnedMe = getIntent();

        if(intentThatSpawnedMe.hasExtra(MovieDetails.EXTRA_IDENTIFIER)) {
            MovieDetails selectedMovie = intentThatSpawnedMe.getParcelableExtra(MovieDetails.EXTRA_IDENTIFIER);

            if(selectedMovie != null)
                Log.v(TAG, selectedMovie.toString());
        }
    }
}
