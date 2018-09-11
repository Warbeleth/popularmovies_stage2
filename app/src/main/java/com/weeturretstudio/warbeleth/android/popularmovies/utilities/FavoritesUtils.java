package com.weeturretstudio.warbeleth.android.popularmovies.utilities;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.weeturretstudio.warbeleth.android.popularmovies.database.DatabaseHelper;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetailsArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoritesUtils {
    private static final String TAG = FavoritesUtils.class.getSimpleName();

    private static FavoritesUtils INSTANCE = null;
    private static DatabaseHelper database = null;
    private Context context = null;
    private MutableLiveData<ArrayList<MovieDetails>> favoriteMovies = null;
    private Observer<ArrayList<MovieDetails>> observer = null;
    private MovieDetailsArrayAdapter movieAdapter = null;


    private void FavoritesUtils(){}

    public static FavoritesUtils getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FavoritesUtils();
        }

        return INSTANCE;
    }

    public FavoritesUtils populateFavorites(Context context, boolean inMemory) {
        this.context = context;
        database = DatabaseHelper.getInstance(context,inMemory);

        Log.v(TAG, "Populating Favorites: ...");
        if(favoriteMovies == null)
            favoriteMovies = new MutableLiveData<ArrayList<MovieDetails>>();
        favoriteMovies.setValue(new ArrayList<MovieDetails>(database.getAllMovies()));
        Log.v(TAG, "Populating Favorites: " + favoriteMovies.getValue().size() + " found.");

        return getInstance();
    }

    public List<MovieDetails> getFavorites() {
        return favoriteMovies.getValue();
    }

    public boolean findInFavorites(int id) {
        if(favoriteMovies != null && favoriteMovies.getValue().size() > 0) {
            for(int index = 0; index < favoriteMovies.getValue().size(); index++) {
                if(favoriteMovies.getValue().get(index).getID() == id)
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

            if(favoriteMovies != null && favoriteMovies.getValue() != null && movie != null) {
                ArrayList<MovieDetails> temp = new ArrayList<>(favoriteMovies.getValue());
                temp.add(movie);
                favoriteMovies.setValue(temp);

                Log.v(TAG, "addFavorite(Save): " + movie.getMovieName());
            }

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

            if(favoriteMovies != null && favoriteMovies.getValue() != null && movie != null) {
                ArrayList<MovieDetails> temp = new ArrayList<>(favoriteMovies.getValue());
                temp.remove(movie);
                favoriteMovies.setValue(temp);
            }
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

    public MovieDetailsArrayAdapter getMovieAdapter() {
        return movieAdapter;
    }

    public void setMovieAdapter(final MovieDetailsArrayAdapter movieAdapter) {
        if(this.movieAdapter != null) {
            favoriteMovies.removeObserver(observer);
        }
        this.movieAdapter = movieAdapter;

        if(this.movieAdapter != null) {
            observer = new Observer<ArrayList<MovieDetails>>() {
                @Override
                public void onChanged(@Nullable ArrayList<MovieDetails> movieDetails) {
                    movieAdapter.clear();
                    if (movieDetails != null)
                        movieAdapter.addAll(movieDetails);
                    movieAdapter.notifyDataSetChanged();
                }
            };

            favoriteMovies.observe((AppCompatActivity) context, observer);

            //Populate to trigger the onChange
            this.populateFavorites(context, false);
        }
    }
}
