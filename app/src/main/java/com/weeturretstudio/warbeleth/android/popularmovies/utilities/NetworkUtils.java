package com.weeturretstudio.warbeleth.android.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

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
    // http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    // Reference: https://developers.themoviedb.org/3/configuration/get-api-configuration

    public static URL getMoviesUrl(Context context) {
        //TODO: Add conditional logic to sort based on User Setting: Popular || Top_Rated
        Uri movieDB = Uri.parse(theMovieDBBaseURL + theMovieDBPopularURL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY_TheMovieDB)
                .build();
        //If A, do X

        //Else if B, do Y

        try {
            URL movieDBQuery = new URL(movieDB.toString());
            return movieDBQuery;
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getStackTrace().toString());
            e.printStackTrace();
            return null;
        }
    }
}
