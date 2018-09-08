package com.weeturretstudio.warbeleth.android.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieReview;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieVideo;

@Database(version = 3,
        entities = { MovieDetails.class, MovieReview.class, MovieVideo.class },
        exportSchema = false)
public abstract class MovieRoom extends RoomDatabase {

    private static final String DATABASE_NAME = "POPULAR_MOVIES";
    private static MovieRoom INSTANCE;

    public abstract MovieDetailsDao MovieDao();
    public abstract MovieReviewDao ReviewDao();

    public static MovieRoom getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MovieRoom.class, DATABASE_NAME)
                    .allowMainThreadQueries() //This is bad? maybe? TODO: Investigate
                    .fallbackToDestructiveMigration() //This is not great; it kills everything. TODO: Investigate
                    .build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
