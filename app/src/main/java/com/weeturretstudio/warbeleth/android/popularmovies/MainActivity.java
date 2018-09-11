package com.weeturretstudio.warbeleth.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetailsArrayAdapter;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.FavoritesUtils;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.JSONUtils;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.NetworkUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MOVIE_API_LOADER_ID = 323272;
    private static boolean loadingDetailsActivity = false;

    MovieDetailsArrayAdapter movieAdapter = null;
    GridView gridView = null;
    private FavoritesUtils favorites = null;
    private List<MovieDetails> currentMovies = null;

    private final String KeyScrollPosition = "KeyScrollPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupGridView();

        /*
        getSupportLoaderManager().initLoader(
                MOVIE_API_LOADER_ID,
                null,
                MainActivity.this);
                */

        // Register the listener
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // Initialize Favorites
        favorites = FavoritesUtils.getInstance().populateFavorites(this, false);
        List<MovieDetails> data = favorites.getFavorites();

        if(data != null){
            Log.v(TAG, "Objects In Database: " + data.size());

            for(int i = 0; i < data.size(); i++)
                Log.v(TAG, "Movie: " + data.get(i).getMovieName());
        }

        //Once everything is ready, begin loading
        for(String key : sharedPreferences.getAll().keySet()) {
            if(key.equals(KeyScrollPosition))
                continue;

            boolean currentValue = sharedPreferences.getBoolean(key, false);

            if(currentValue) {
                loadFromDatabaseOrNetwork(key);
                break;
            }
        }
    }

    @Override
    public void onPause() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putInt(KeyScrollPosition, gridView.getFirstVisiblePosition()).apply();

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(KeyScrollPosition))
            return;

        boolean isChecked = sharedPreferences.getBoolean(key, false);
        Log.v(TAG, "Key: " + key + " is checked: " + isChecked);

        if(isChecked)
            loadFromDatabaseOrNetwork(key);
    }

    private void loadFromDatabaseOrNetwork(String key) {
        //IF we're sorting by favorites, which are stored locally... use database instead of loader.
        Log.v(TAG, "LoadFromDatabaseOrNetwork: Current Key is " + key);

        if(key.equals(this.getString(R.string.key_favoritemovies))) {
            getSupportLoaderManager().destroyLoader(MOVIE_API_LOADER_ID);
            Log.v(TAG, "LoadFromDatabaseOrNetwork: Load from database");
            currentMovies = favorites.getFavorites();
            //setupViewAfterLoading();
            favorites.setMovieAdapter(movieAdapter);
            updateScrollPosition();
        }
        else {
            favorites.setMovieAdapter(null);
            Log.v(TAG, "LoadFromDatabaseOrNetwork: Load from network");
            getSupportLoaderManager().restartLoader(
                    MOVIE_API_LOADER_ID,
                    null,
                    MainActivity.this);
        }
    }

    private void updateScrollPosition() {
        if(movieAdapter.getCount() > 0) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            gridView.setSelection(sharedPreferences.getInt(KeyScrollPosition, 0));
        }
    }

    private void setupViewAfterLoading() {
        movieAdapter.clear();
        movieAdapter.addAll(currentMovies);
        movieAdapter.notifyDataSetChanged();
    }

    private void setupGridView() {
        movieAdapter = new MovieDetailsArrayAdapter(
                MainActivity.this, new ArrayList<MovieDetails>());
        gridView = MainActivity.this.findViewById(R.id.main_gridview);
        gridView.setAdapter(movieAdapter);
        //Generate onItemClick listener to handle clicks
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!loadingDetailsActivity) {
                    loadingDetailsActivity = true;
                    MovieDetails nestedDetails = (MovieDetails) parent.getItemAtPosition(position);
                    Intent launchDetailsActivity = new Intent(parent.getContext(), MovieDetailsActivity.class);
                    launchDetailsActivity.putExtra(MovieDetails.EXTRA_IDENTIFIER, nestedDetails);
                    startActivity(launchDetailsActivity);
                    loadingDetailsActivity = false;
                }
            }
        });
    }

    private static class MovieLoader extends AsyncTaskLoader<String> {

        private final WeakReference<MainActivity> mainActivity;
        private String mMovieData;

        MovieLoader(MainActivity mainActivity) {
            super(mainActivity);
            this.mainActivity = new WeakReference<>(mainActivity);
        }

        @Override
        protected void onStartLoading() {
            if(mMovieData != null)
                deliverResult(mMovieData);
            else
                forceLoad();
        }

        @Override
        public void deliverResult(String data) {
            mMovieData = data;
            super.deliverResult(data);
        }

        @Override
        public String loadInBackground() {
            Log.v(TAG, "LoadInBackground: ");
            URL apiURL = NetworkUtils.getMoviesUrl(mainActivity.get());

            if(apiURL == null)
                return null;

            try {
                String httpResultString = NetworkUtils.getResponseFromHttpUrl(getContext(), apiURL);
                Log.v(TAG, "Popular Movies: " + httpResultString);
                return httpResultString;
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data != null) {
            Log.v(TAG, data);

            //httpResultString = placeHolderPopular;
            JSONObject parsedObject = JSONUtils.parseStringToJSON(data);
            currentMovies = JSONUtils.parseMovies(parsedObject);
            Log.v(TAG, "Current Movies:");
            for(int i = 0; i < currentMovies.size(); i++)
                Log.v(TAG, "Movie["+i+"]: " + currentMovies.get(i).getMovieName() + "\n");
        }

        setupViewAfterLoading();
        updateScrollPosition();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        //TODO: What should be done on reset?
        Log.v(TAG, "OnLoaderReset: ");
    }
}
