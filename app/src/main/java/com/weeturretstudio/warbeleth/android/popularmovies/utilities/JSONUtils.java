package com.weeturretstudio.warbeleth.android.popularmovies.utilities;

import android.util.Log;

import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieReview;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONUtils {
    private static final String TAG = JSONUtils.class.getSimpleName();

    //Movie RESULT values
    private static final String KEY_JSON_PAGE = "page";
    private static final String KEY_JSON_RESULTS = "results";
    private static final String KEY_JSON_ID = "id";
    private static final String KEY_JSON_TITLE = "title";
    private static final String KEY_JSON_TITLE_ORIGINAL = "original_title";
    private static final String KEY_JSON_POSTER_PATH = "poster_path";
    private static final String KEY_JSON_OVERVIEW = "overview";
    private static final String KEY_JSON_RELEASE_DATE = "release_date";
    private static final String KEY_JSON_RATING = "vote_average";

    //Review RESULT values
    private static final String KEY_REVIEW_JSON_ID = "id";
    private static final String KEY_REVIEW_JSON_URL = "url";
    private static final String KEY_REVIEW_JSON_AUTHOR = "author";
    private static final String KEY_REVIEW_JSON_CONTENT = "content";

    //Video RESULT values
    private static final String KEY_VIDEO_JSON_ID = "id";
    private static final String KEY_VIDEO_JSON_KEY = "key";
    private static final String KEY_VIDEO_JSON_NAME = "name";
    private static final String KEY_VIDEO_JSON_SITE = "site";
    private static final String KEY_VIDEO_JSON_SIZE = "size";
    private static final String KEY_VIDEO_JSON_TYPE = "type";

    public static JSONObject parseStringToJSON(String jsonString) {
        JSONObject result = null;

        if(jsonString == null || jsonString.length() == 0)
            return null;

        try {
            result = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.e(TAG, "parseStringToJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static List<MovieDetails> parseMovies(JSONObject parseMe) {
        try {
            Log.v(TAG, "parseMovies: " + parseMe.toString());
            JSONArray resultsArray = parseMe.getJSONArray(KEY_JSON_RESULTS);
            int numResults = resultsArray.length();
            MovieDetails[] movieData = new MovieDetails[numResults];

            for(int i = 0; i < numResults; i++) {
                JSONObject currentMovie = resultsArray.getJSONObject(i);

                movieData[i] = new MovieDetails();
                movieData[i].setID(currentMovie.getInt(KEY_JSON_ID));
                movieData[i].setMovieName(currentMovie.getString(KEY_JSON_TITLE_ORIGINAL));
                movieData[i].setPosterPath(currentMovie.getString(KEY_JSON_POSTER_PATH));
                movieData[i].setReleaseDate(currentMovie.getString(KEY_JSON_RELEASE_DATE));
                movieData[i].setOverview(currentMovie.getString(KEY_JSON_OVERVIEW));
                movieData[i].setRating(currentMovie.getString(KEY_JSON_RATING));
            }

            return Arrays.asList(movieData);

        } catch (JSONException e) {
            Log.e(TAG, "parseMovies: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            Log.e(TAG, "parseMovies: - Unexpected Exception " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<MovieReview> parseReviews(JSONObject parseMe) {
        try {
            Log.v(TAG, "parseReviews: " + parseMe.toString());

            JSONArray resultsArray = parseMe.getJSONArray(KEY_JSON_RESULTS);
            int numResults = resultsArray.length();
            ArrayList<MovieReview> movieData = new ArrayList<>(numResults);

            for(int i = 0; i < numResults; i++) {
                JSONObject currentReview = resultsArray.getJSONObject(i);

                MovieReview review = new MovieReview();
                review.setReviewID(currentReview.getString(KEY_REVIEW_JSON_ID));
                review.setURL(currentReview.getString(KEY_REVIEW_JSON_URL));
                review.setAuthor(currentReview.getString(KEY_REVIEW_JSON_AUTHOR));
                review.setContent(currentReview.getString(KEY_REVIEW_JSON_CONTENT));

                movieData.add(review);
            }

            return movieData;

        } catch (JSONException e) {
            Log.e(TAG, "parseReviews: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            Log.e(TAG, "parseReviews: - Unexpected Exception " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<MovieVideo> parseVideos(JSONObject parseMe) {
        try {
            Log.v(TAG, "parseVideos: " + parseMe.toString());

            JSONArray resultsArray = parseMe.getJSONArray(KEY_JSON_RESULTS);
            int numResults = resultsArray.length();
            ArrayList<MovieVideo> movieData = new ArrayList<>(numResults);

            for(int i = 0; i < numResults; i++) {
                JSONObject currentVideo = resultsArray.getJSONObject(i);

                MovieVideo video = new MovieVideo();

                video.setVidID(currentVideo.getString(KEY_VIDEO_JSON_ID));
                video.setKey(currentVideo.getString(KEY_VIDEO_JSON_KEY));
                video.setName(currentVideo.getString(KEY_VIDEO_JSON_NAME));
                video.setSite(currentVideo.getString(KEY_VIDEO_JSON_SITE));
                video.setSize(currentVideo.getInt(KEY_VIDEO_JSON_SIZE));
                video.setType(currentVideo.getString(KEY_VIDEO_JSON_TYPE));

                movieData.add(video);
            }

            return movieData;

        } catch (JSONException e) {
            Log.e(TAG, "parseVideos: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            Log.e(TAG, "parseVideos: - Unexpected Exception " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
