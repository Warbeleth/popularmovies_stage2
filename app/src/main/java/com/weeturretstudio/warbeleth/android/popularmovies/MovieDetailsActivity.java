package com.weeturretstudio.warbeleth.android.popularmovies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.NetworkUtils;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.UrlAsyncLoader;

public class MovieDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
                    ((TextView)findViewById(R.id.tv_details_release_date_value)).setText(selectedMovie.getReleaseDate());

                if(selectedMovie.getRating() != null)
                    ((TextView)findViewById(R.id.tv_details_rating_value)).setText(selectedMovie.getRating());

                if(selectedMovie.getOverview() != null)
                    ((TextView)findViewById(R.id.tv_ml_details_overview_value)).setText(selectedMovie.getOverview());

                if(selectedMovie.getPosterPath() != null)
                    Picasso.with(this).load(NetworkUtils.getPosterUri(selectedMovie.getPosterPath()))
                        .fit()
                        .centerInside()
                        .into(((ImageView)findViewById(R.id.iv_details_movie_poster)));
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        //TODO: Pass correct URL based on ID?
        return new UrlAsyncLoader(this, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        //TODO: Parse result based on loader's ID?
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
