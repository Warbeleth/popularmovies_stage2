package com.weeturretstudio.warbeleth.android.popularmovies;

import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.NetworkUtils;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intentThatSpawnedMe = getIntent();

        if(intentThatSpawnedMe.hasExtra(MovieDetails.EXTRA_IDENTIFIER)) {
            MovieDetails selectedMovie = intentThatSpawnedMe.getParcelableExtra(MovieDetails.EXTRA_IDENTIFIER);

            if(selectedMovie != null) {
                //Time to log what we have, and then create our view!
                Log.v(TAG, selectedMovie.toString());

                /*
                        ImageView thumbnail = (ImageView)convertView;
        Picasso.with(getContext()).load(
                NetworkUtils.getPosterUri(getContext(), movieDetails.gePosterPath()))
                .into(thumbnail);
                 */

                if(selectedMovie.getMovieName() != null)
                    ((TextView)findViewById(R.id.tv_details_movie_title)).setText(selectedMovie.getMovieName());

                if(selectedMovie.getReleaseDate() != null)
                    ((TextView)findViewById(R.id.tv_details_release_date)).setText(selectedMovie.getReleaseDate());

                if(selectedMovie.getRating() != null)
                    ((TextView)findViewById(R.id.tv_details_rating)).setText(selectedMovie.getRating());

                if(selectedMovie.getOverview() != null)
                    ((TextView)findViewById(R.id.tv_ml_details_overview)).setText(selectedMovie.getOverview());

                if(selectedMovie.getPosterPath() != null)
                    Picasso.with(this).load(NetworkUtils.getPosterUri(this, selectedMovie.getPosterPath()))
                        .into(((ImageView)findViewById(R.id.iv_details_movie_poster)));
            }
        }
    }
}
