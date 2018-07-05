package com.weeturretstudio.warbeleth.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.weeturretstudio.warbeleth.android.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MOVIE_API_LOADER_ID = 323272;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportLoaderManager().initLoader(MOVIE_API_LOADER_ID, null, MainActivity.this);
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
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            private String mMovieData;

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
                URL apiURL = NetworkUtils.getMoviesUrl(MainActivity.this);

                try {
                    String popularMovies = NetworkUtils.getResponseFromHttpUrl(apiURL);
                    Log.v(TAG, "Popular Movies: " + popularMovies);
                    return popularMovies;
                } catch (IOException e) {
                    Log.e(TAG, e.getStackTrace().toString());
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if(data != null) {
            Log.v(TAG, data);
            //TODO: Set future adapter and/or update UI for errors.
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        //TODO: What should be done on reset?
    }
}
