package com.weeturretstudio.warbeleth.android.popularmovies.utilities;

import android.content.Context;
import android.util.Log;

import com.weeturretstudio.warbeleth.android.popularmovies.database.DatabaseHelper;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;

import java.util.ArrayList;
import java.util.List;

public class FavoritesUtils {
    private static final String TAG = FavoritesUtils.class.getSimpleName();

    private static FavoritesUtils INSTANCE = null;
    private static DatabaseHelper database = null;
    private ArrayList<MovieDetails> favoriteMovies = null;


    private FavoritesUtils(){}

    public static FavoritesUtils getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FavoritesUtils();
        }

        return INSTANCE;
    }

    public FavoritesUtils populateFavorites(Context context, boolean inMemory) {
        database = DatabaseHelper.getInstance(context,inMemory);

        Log.v(TAG, "Populating Favorites: ...");
        favoriteMovies = new ArrayList<>(database.getAllMovies());
        Log.v(TAG, "Populating Favorites: " + favoriteMovies.size() + " found.");

        return getInstance();
    }

    public List<MovieDetails> getFavorites() {
        return favoriteMovies;
    }

    public boolean findInFavorites(int id) {
        if(favoriteMovies != null && favoriteMovies.size() > 0) {
            for(int index = 0; index < favoriteMovies.size(); index++) {
                if(favoriteMovies.get(index).getID() == id)
                    return true;
            }
        }

        return false;
    }

    public boolean findInFavorites(MovieDetails movie) {
        return findInFavorites(movie.getID());
    }

    public void addFavorite(MovieDetails movie) {
        if(!findInFavorites(movie)) {
            if(database != null)
                database.saveMovie(movie);

            favoriteMovies.add(movie);

            Log.v(TAG, "addFavorite(Save): " + movie.getMovieName());
        }
        else {
            if (database != null) {
                database.updateMovie(movie);
                Log.v(TAG, "addFavorite(Update): " + movie.getMovieName());
            }
        }
    }

    public void removeFavorite(MovieDetails movie) {
        if(findInFavorites(movie)) {
            if(database != null) {
                database.deleteMovie(movie);
                Log.v(TAG, "removeFavorite(Delete): " + movie.getMovieName());
            }

            favoriteMovies.remove(movie);
        }
    }

    /**
     * Save or Remove:
     * If the movie is in favorites, it is removed. If not, it is added.
     * @param movie the movie to search for removal, or addition
     * @return True if added, False if removed.
     */
    public boolean saveOrRemoveFavorite(MovieDetails movie) {
        if(findInFavorites(movie)) {
            removeFavorite(movie);
            Log.v(TAG, "saveOrRemoveFavorite(Remove): " + movie.getMovieName());
            return false;
        }
        else {
            addFavorite(movie);
            Log.v(TAG, "saveOrRemoveFavorite(Add): " + movie.getMovieName());
            return true;
        }
    }
}
