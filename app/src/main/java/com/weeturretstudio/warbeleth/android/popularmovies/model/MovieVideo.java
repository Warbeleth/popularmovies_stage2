package com.weeturretstudio.warbeleth.android.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

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
@Entity(tableName = "tbl_related_video")
public class MovieVideo implements Parcelable {

    @PrimaryKey
    @NonNull
    private String VidID;
    private String Key;
    private String Name;
    private String Site;
    private int Size; //Allowed Values: 360, 480, 720, 1080
    private String Type; //Allowed Values: Trailer, Teaser, Clip, Featurette

    @Ignore
    public MovieVideo(){}

    public MovieVideo(@NonNull String VidID, String Key, String Name, String Site, int Size, String Type)
    {
        this.VidID = VidID;
        this.Key = Key;
        this.Name = Name;
        this.Site = Site;
        this.Size = Size;
        this.Type = Type;
    }

    @Ignore
    protected MovieVideo(Parcel in) {
        VidID = in.readString();
        Key = in.readString();
        Name = in.readString();
        Site = in.readString();
        Size = in.readInt();
        Type = in.readString();
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

    @NonNull
    public String getVidID() {
        return VidID;
    }

    public void setVidID(@NonNull String vidID) {
        this.VidID = vidID;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        this.Site = site;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        this.Size = size;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(VidID);
        dest.writeString(Key);
        dest.writeString(Name);
        dest.writeString(Site);
        dest.writeInt(Size);
        dest.writeString(Type);
    }
}
