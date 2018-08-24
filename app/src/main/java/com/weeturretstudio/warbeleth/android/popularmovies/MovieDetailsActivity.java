package com.weeturretstudio.warbeleth.android.popularmovies;

import android.content.Intent;
import android.net.Network;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieReviewAdapter;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieVideoAdapter;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.JSONUtils;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.NetworkUtils;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.UrlAsyncLoader;

public class MovieDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    private static final int REVIEW_ENDPOINT_ID = 100;
    private static final int TRAILER_ENDPOINT_ID = 101;
    private MovieDetails currentMovie = null;
    private RecyclerView trailerView = null;
    private RecyclerView reviewView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        trailerView = (RecyclerView)findViewById(R.id.Trailers_ScrollView);
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this );
                                        //LinearLayoutManager.HORIZONTAL, false);
        trailerView.setLayoutManager(trailerLayoutManager);
        //TODO: Create Adapter for trailers.
        MovieVideoAdapter mvAdapter = new MovieVideoAdapter(null);
        trailerView.setAdapter(mvAdapter);

        reviewView = (RecyclerView)findViewById(R.id.Reviews_Scrollview);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this);
        reviewView.setLayoutManager(verticalLayoutManager);
        //TODO: Create adapter for reviews.
        MovieReviewAdapter mrAdapter = new MovieReviewAdapter(null);
        reviewView.setAdapter(mrAdapter);

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

                //Store the currently selected movie.
                currentMovie = selectedMovie;

                getSupportLoaderManager().initLoader(REVIEW_ENDPOINT_ID,
                        null,this);

                getSupportLoaderManager().initLoader(TRAILER_ENDPOINT_ID,
                        null,this);

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
        if(id == REVIEW_ENDPOINT_ID) {
            Log.v(TAG, "CreateLoader: " + id + " corresponding to: " + "REVIEW_ENDPOINT");
            return new UrlAsyncLoader(this, NetworkUtils.getReviewsUrl(currentMovie));
        }
        else {
            Log.v(TAG, "CreateLoader: " + id + " corresponding to: " + "TRAILER_ENDPOINT");
            return new UrlAsyncLoader(this, NetworkUtils.getVideosUrl(currentMovie));
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(loader.getId() == REVIEW_ENDPOINT_ID) {
            Log.v(TAG, "LoadFinished for REVIEW endpoint: " + data);
            currentMovie.setReviews(JSONUtils.parseReviews(JSONUtils.parseStringToJSON(data)));
            //ScrollView reviews = findViewById(R.id.Reviews_Scrollview);
            ((MovieReviewAdapter)reviewView.getAdapter()).setReviews(currentMovie.getRelatedReviews());
        }
        else if(loader.getId() == TRAILER_ENDPOINT_ID) {
            Log.v(TAG, "LoadFinished for TRAILER endpoint: " + data);
            currentMovie.setRelatedvideos(JSONUtils.parseVideos(JSONUtils.parseStringToJSON(data)));

            ((MovieVideoAdapter)trailerView.getAdapter()).setVideos(currentMovie.getRelatedVideos());
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
