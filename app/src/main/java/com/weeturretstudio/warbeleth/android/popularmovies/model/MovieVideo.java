package com.weeturretstudio.warbeleth.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
Sample JSON
{
  "id": 550,
  "results": [
    {
      "id": "533ec654c3a36854480003eb",
      "iso_639_1": "en",
      "iso_3166_1": "US",
      "key": "SUXWAEX2jlg",
      "name": "Trailer 1",
      "site": "YouTube",
      "size": 720,
      "type": "Trailer"
    }
  ]
}
*/
public class MovieVideo implements Parcelable {
    private int mID;
    private String mVidID;
    private String mKey;
    private String mName;
    private String mSite;
    private int mSize; //Allowed Values: 360, 480, 720, 1080
    private String mType; //Allowed Values: Trailer, Teaser, Clip, Featurette

    protected MovieVideo(Parcel in) {
        mID = in.readInt();
        mVidID = in.readString();
        mKey = in.readString();
        mName = in.readString();
        mSite = in.readString();
        mSize = in.readInt();
        mType = in.readString();
    }

    public static final Creator<MovieVideo> CREATOR = new Creator<MovieVideo>() {
        @Override
        public MovieVideo createFromParcel(Parcel in) {
            return new MovieVideo(in);
        }

        @Override
        public MovieVideo[] newArray(int size) {
            return new MovieVideo[size];
        }
    };

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmVidID() {
        return mVidID;
    }

    public void setmVidID(String mVidID) {
        this.mVidID = mVidID;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSite() {
        return mSite;
    }

    public void setmSite(String mSite) {
        this.mSite = mSite;
    }

    public int getmSize() {
        return mSize;
    }

    public void setmSize(int mSize) {
        this.mSize = mSize;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mID);
        dest.writeString(mVidID);
        dest.writeString(mKey);
        dest.writeString(mName);
        dest.writeString(mSite);
        dest.writeInt(mSize);
        dest.writeString(mType);
    }
}
