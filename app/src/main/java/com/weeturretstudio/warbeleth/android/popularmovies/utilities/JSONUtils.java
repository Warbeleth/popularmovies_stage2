package com.weeturretstudio.warbeleth.android.popularmovies.utilities;

import android.util.Log;

import com.weeturretstudio.warbeleth.android.popularmovies.model.BasicMovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

public class JSONUtils {
    private static final String TAG = JSONUtils.class.getSimpleName();

    private static final String KEY_JSON_PAGE = "page";
    private static final String KEY_JSON_RESULTS = "results";
    private static final String KEY_JSON_ID = "id";
    private static final String KEY_JSON_TITLE = "title";
    private static final String KEY_JSON_TITLE_ORIGINAL = "original_title";
    private static final String KEY_JSON_POSTER_PATH = "poster_path";
    private static final String KEY_JSON_OVERVIEW = "overview";

    private enum MOVIEDB_RESULT_OPTIONS { vote_count, id, video, vote_Average,
                                            title, popularity, poster_path, original_language,
                                            original_title, genre_ids, backdrop_path, adult,
                                            overview, release_date };


    public static JSONObject parseStringToJSON(String jsonString) {
        JSONObject result = null;
        try {
            result = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.e(TAG, "parseStringToJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static BasicMovieData[] parseMovies(JSONObject parseMe) {
        Log.v(TAG, "parseMovies: " + parseMe.toString());
        //TODO: Implement JSON parsing logic.
        if (parseMe == null)
            return null;

        try {
            JSONArray resultsArray = parseMe.getJSONArray(KEY_JSON_RESULTS);
            int numResults = resultsArray.length();
            BasicMovieData[] movieData = new BasicMovieData[numResults];

            for(int i = 0; i < numResults; i++) {
                JSONObject currentMovie = resultsArray.getJSONObject(i);

                movieData[i] = new BasicMovieData();
                movieData[i].setmID(currentMovie.getInt(KEY_JSON_ID));
                movieData[i].setmMovieName(currentMovie.getString(KEY_JSON_TITLE_ORIGINAL));
                movieData[i].setmPosterPath(currentMovie.getString(KEY_JSON_POSTER_PATH));
            }

            return movieData;
        } catch (JSONException e) {
            Log.e(TAG, "parseMovies: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
