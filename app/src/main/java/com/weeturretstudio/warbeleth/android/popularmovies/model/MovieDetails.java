package com.weeturretstudio.warbeleth.android.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

@Entity(tableName = "tbl_movie")
public class MovieDetails implements Parcelable {

    public static final String EXTRA_IDENTIFIER = "KEY_MovieDetails";

    @PrimaryKey
    private int mID;
    private String mMovieName;                      //original_title
    private String mPosterPath;                     //poster_path
    private String mOverview;                       //overview
    private String mReleaseDate;                    //release_date
    private String mRating;                         //vote_average
    private ArrayList<MovieReview> mReviews;        //Reviews
    private ArrayList<MovieVideo> mRelatedvideos;   //Videos


    @Ignore
    public MovieDetails(){}

    public MovieDetails(int id, String name, String poster, String overview, String releaseDate, String rating,
                        ArrayList<MovieReview> reviews, ArrayList<MovieVideo> videos) {
        mID = id;
        mMovieName = name;
        mPosterPath = poster;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mRating = rating;
        mReviews = reviews;
        mRelatedvideos = videos;
    }

    protected MovieDetails(Parcel in) {
        mID = in.readInt();
        mMovieName = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mRating = in.readString();
        mReviews = in.createTypedArrayList(MovieReview.CREATOR);
        mRelatedvideos = in.createTypedArrayList(MovieVideo.CREATOR);
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

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public String getMovieName() {
        return mMovieName;
    }

    public void setMovieName(String mMovieName) {
        this.mMovieName = mMovieName;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String mRating) {
        this.mRating = mRating;
    }

    public void setReviews(ArrayList<MovieReview> reviews) { this.mReviews = reviews; }

    public ArrayList<MovieReview> getRelatedReviews() { return this.mReviews; }

    public void setRelatedvideos(ArrayList<MovieVideo> relatedvideos) { this.mRelatedvideos = relatedvideos; }

    public ArrayList<MovieVideo> getRelatedVideos() { return this.mRelatedvideos; }

    @Override
    public String toString() {
        return ("ID: " + mID + "\n" +
                "Title: " + mMovieName + "\n" +
                "PosterPath: " + mPosterPath + "\n" +
                "Overview: " + mOverview + "\n" +
                "Release Date: " + mReleaseDate + "\n" +
                "Rating: " + mRating + "\n");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mID);
        dest.writeString(mMovieName);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
        dest.writeString(mRating);
        dest.writeTypedList(mReviews);
        dest.writeTypedList(mRelatedvideos);
    }
}
