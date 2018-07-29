package com.weeturretstudio.warbeleth.android.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.weeturretstudio.warbeleth.android.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    //TheMovieDB: API Key
    //TODO: Reviewer (or self), be sure this API key is removed and that the-
    // string "INSERT_API_KEY_HERE" is present prior to being committed.
    private static final String API_KEY_PARAM = "api_key";
    private static final String API_KEY_TheMovieDB = "INSERT_API_KEY_HERE";

    //TheMovieDB: Base + Parameter URLs
    private static final String theMovieDBBaseURL = "http://api.themoviedb.org/3/movie";
    private static final String theMovieDBPopularURL = "/popular";
    private static final String theMovieDBTopRatedURL = "/top_rated";

    //TheMovieDB CDN: Base + configuration URLs
    private static final String imageTMDBBaseURL = "http://image.tmdb.org/t/p/";
    //Options: a ‘size’, which will be one of the following: "w92", "w154", "w185", "w342", "w500", "w780", or "original".
    // For most phones we recommend using “w185”.
    private static final String imageSizeParameter = "w185";
    //Full Example URL:
    // http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    // Reference: https://developers.themoviedb.org/3/configuration/get-api-configuration

    public static URL getMoviesUrl(Context context) {
        boolean sortByPopularity = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
                context.getString(R.string.key_popularmovies),
                context.getResources().getBoolean(R.bool.preference_PopularMovies_Default));


        //TODO: We will be implementing a third option, that is locally stored: 'Favorites'.
        // These favorites will need an alternate input form, or to not even use the network.
        Uri movieDB = Uri.parse(theMovieDBBaseURL +
                ((sortByPopularity) ? theMovieDBPopularURL : theMovieDBTopRatedURL))
                .buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY_TheMovieDB)
                .build();
        //If A, do X

        //Else if B, do Y

        try {
            return new URL(movieDB.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Uri getPosterUri(String posterPath) {
        return Uri.parse(imageTMDBBaseURL + imageSizeParameter + posterPath)
                .buildUpon()
                .build();
    }

    public static String getResponseFromHttpUrl(Context context, URL url) throws IOException {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm == null)
            return null;

        NetworkInfo info = cm.getActiveNetworkInfo();

        if(info == null || !info.isConnectedOrConnecting())
            return null;

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}