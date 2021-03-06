package com.weeturretstudio.warbeleth.android.popularmovies.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;

import java.util.List;

@Dao
public interface MovieDetailsDao {

    @Query("select * from tbl_movie where ID = :id")
    MovieDetails selectMovieByID(int id);

    @Query("select * from tbl_movie")
    List<MovieDetails> selectAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieDetails movieDetails);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<MovieDetails> movieDetails);

    @Update
    void update(MovieDetails movieDetails);

    @Delete
    void delete(MovieDetails movieDetails);
}
