package com.weeturretstudio.warbeleth.android.popularmovies.database;

import android.content.Context;
import android.support.annotation.NonNull;

import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieReview;

import java.util.ArrayList;
import java.util.List;

/*
Reference for the idea and general implementation of a DatabaseHelper to pair relations:
https://proandroiddev.com/android-room-handling-relations-using-livedata-2d892e40bd53
 */
public class DatabaseHelper {
    public static DatabaseHelper INSTANCE = null;
    private static MovieRoom database = null;

    public static DatabaseHelper getInstance(Context context, boolean inMemory) {

        if(database == null)
            database = MovieRoom.getDatabase(context);

        //Once initialized, return an instance - this is to simplify setup and first usage.
        return getInstance();
    }

    private static DatabaseHelper getInstance() {
        if(INSTANCE == null)
        {
            INSTANCE = new DatabaseHelper();
        }

        return INSTANCE;
    }

    public MovieDetails getMovieWithID(int id) {
        MovieDetails movie = database.MovieDao().selectMovieByID(id);
        List<MovieReview> reviews = database.ReviewDao().selectAllReviewsForMovie(id);

        movie.setReviews(new ArrayList<MovieReview>(reviews));

        return movie;
    }

    public void saveMovie(MovieDetails movie) {
        database.MovieDao().insert(movie);

        for(int i = 0; i < movie.getReviews().size(); i++)
            database.ReviewDao().insert(movie.getReviews().get(i));
    }

    public List<MovieDetails> getAllMovies() {
        List<MovieDetails> movies = database.MovieDao().selectAllMovies();

        for(int i = 0; i < movies.size(); i++) {
            List<MovieReview> reviews = database.ReviewDao().selectAllReviewsForMovie(movies.get(i).getID());
            movies.get(i).setReviews(new ArrayList<MovieReview>(reviews));
        }

        return movies;
    }

    public void saveMovies(@NonNull List<MovieDetails> movies) {
        for(int index = 0; index < movies.size(); index++) {
            database.MovieDao().insert(movies.get(index));

            for(int reviewIndex = 0; reviewIndex < movies.get(index).getReviews().size(); reviewIndex++)
                database.ReviewDao().insert(movies.get(index).getReviews().get(reviewIndex));
        }
    }
}
