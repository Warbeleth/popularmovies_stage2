package com.weeturretstudio.warbeleth.android.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.weeturretstudio.warbeleth.android.popularmovies.database.converters.MovieVideoConverter;

import java.util.ArrayList;

@Entity(tableName = "tbl_movie")
public class MovieDetails implements Parcelable {

    public static final String EXTRA_IDENTIFIER = "KEY_MovieDetails";

    @PrimaryKey
    private int ID;
    private String MovieName;                      //original_title
    private String PosterPath;                     //poster_path
    private String Overview;                       //overview
    private String ReleaseDate;                    //release_date
    private String Rating;                         //vote_average
    @Ignore
    private ArrayList<MovieReview> Reviews;        //Reviews

    @TypeConverters(MovieVideoConverter.class)
    private ArrayList<MovieVideo> Videos;   //Videos


    @Ignore
    public MovieDetails(){}

    public MovieDetails(int ID, String MovieName, String PosterPath, String Overview, String ReleaseDate, String Rating) {
        this.ID = ID;
        this.MovieName = MovieName;
        this.PosterPath = PosterPath;
        this.Overview = Overview;
        this.ReleaseDate = ReleaseDate;
        this.Rating = Rating;
    }

    @Ignore
    public MovieDetails(int id, String name, String poster, String overview, String releaseDate, String rating,
                        ArrayList<MovieReview> reviews, ArrayList<MovieVideo> videos) {
        ID = id;
        MovieName = name;
        PosterPath = poster;
        Overview = overview;
        ReleaseDate = releaseDate;
        Rating = rating;
        Reviews = reviews;
        Videos = videos;
    }

    @Ignore
    protected MovieDetails(Parcel in) {
        ID = in.readInt();
        MovieName = in.readString();
        PosterPath = in.readString();
        Overview = in.readString();
        ReleaseDate = in.readString();
        Rating = in.readString();
        Reviews = in.createTypedArrayList(MovieReview.CREATOR);
        Videos = in.createTypedArrayList(MovieVideo.CREATOR);
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;

        if(!(o instanceof MovieDetails))
            return false;

        MovieDetails movie = (MovieDetails)o;
        return movie.getID() == this.getID();
    }

    public int getID() {
        return ID;
    }

    public void setID(int mID) {
        this.ID = mID;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String mMovieName) {
        this.MovieName = mMovieName;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.PosterPath = mPosterPath;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String mOverview) {
        this.Overview = mOverview;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.ReleaseDate = mReleaseDate;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String mRating) {
        this.Rating = mRating;
    }

    public void setReviews(ArrayList<MovieReview> reviews) {
        this.Reviews = reviews;

        if(this.Reviews != null) {
            for (int i = 0; i < this.Reviews.size(); i++)
                this.Reviews.get(i).setMovieID(this.ID);
        }
    }

    public ArrayList<MovieReview> getReviews() { return this.Reviews; }

    public void setVideos(ArrayList<MovieVideo> videos) { this.Videos = videos; }

    public ArrayList<MovieVideo> getVideos() { return this.Videos; }

    @Override
    public String toString() {
        return ("ID: " + ID + "\n" +
                "Title: " + MovieName + "\n" +
                "PosterPath: " + PosterPath + "\n" +
                "Overview: " + Overview + "\n" +
                "Release Date: " + ReleaseDate + "\n" +
                "Rating: " + Rating + "\n");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(MovieName);
        dest.writeString(PosterPath);
        dest.writeString(Overview);
        dest.writeString(ReleaseDate);
        dest.writeString(Rating);
        dest.writeTypedList(Reviews);
        dest.writeTypedList(Videos);
    }
}
