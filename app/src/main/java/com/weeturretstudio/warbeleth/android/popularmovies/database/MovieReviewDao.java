package com.weeturretstudio.warbeleth.android.popularmovies.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieReview;

import java.util.List;

@Dao
public interface MovieReviewDao {

    @Query("select * from tbl_review where ReviewID = :id")
    MovieReview selectReviewByID(String id);

    @Query("select * from tbl_review where MovieID = :id")
    List<MovieReview> selectAllReviewsForMovie(int id);

    @Query("select * from tbl_review")
    List<MovieReview> selectAllReviews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieReview movieReview);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<MovieReview> movieReviews);

    @Update
    void update(MovieReview movieReview);

    @Update
    void update(List<MovieReview> movieReviews);

    @Delete
    void delete(MovieReview movieReview);

    @Delete
    void delete(List<MovieReview> movieReviews);
}
