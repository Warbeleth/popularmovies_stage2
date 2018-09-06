package com.weeturretstudio.warbeleth.android.popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/*
Sample JSON
{
  "id": 297761,
  "page": 1,
  "results": [
    {
      "id": "57a814dc9251415cfb00309a",
      "author": "Frank Ochieng",
      "content": "Summertime 2016 has not been very kind to DC Comics-based personalities looking to shine consistently like their big screen Marvel Comics counterparts. Following the super-sized dud that was _Batman v. Superman: Dawn of Justice_ released a few months ago must really put some major pressure on Warner Bros. to gamble on ensuring that the presence of **Suicide Squad** does not meet the same kind of indifferent reception. Well, it turns out that although the anticipation was high for writer-director David Ayer's supervillain saga involving high-powered imprisoned rogues recruited as U.S. governmental operatives out to stop other skillful baddies (as it was for Zack Ryder's aforementioned \"Dawn of Justice\") the concoction of **Suicide Squad** feels like a colorful mishmash of collective misfits laboriously taking up space in a disjointed eye candy-coated spectacle that never manages to match its intended sizzle.\r\n\r\nOne would think that the premise for **Suicide Squad** would tap into the intriguing naughtiness with more robust gumption given the collection of super-powered oddballs asked to be immediate anti-heroes in this toothless jamboree of renegade rejects. Strangely, the grim and brooding presentation of **Suicide Squad** is more of an erratic downer than a hyperactive high-wire act as intended at the creative hands of Ayer. There is no reason why this lively group of adventurous agitators should appear so flat and inconsequential in a boisterous blockbuster that sporadically limps.\r\n\r\nGiven the twisted members that comprise this elite team of terrorizing tools it is very disappointing to see how **Suicide Squad** struggles with its so-called subversive themes. Sadly, this splattered mess never firmly grasps its bid for distinctive irreverence or off-balance exploitation. Instead, **Squad** feels strained in its execution and we are never really invested in entirely watching these treasured troublemakers find redemption because the story is soggy and uninspired. Furthermore, not all of the **Squad** participants are fleshed out satisfyingly for us to get behind with thirsty cynicism. The headlining leads in Will Smith's Floyd Lawton/Deadshot, Oscar-winner Jared Leto's green-haired Joker and Australian beauty Margot Robbie's Harleen Quinzel/Harley Quinn get the meaty standout parts while the lesser known supporting cast get stuck with chewing on the thankless remaining bone while seemingly acting as background furniture to the bigger names.\r\n\r\nNaturally, desperation has set in for the U.S. government as they need to safeguard national security against advanced sinister forces that threaten the fiber of American self-interests everywhere. What better way to hire gifted protection than to consider employing the world's most incarcerated corruptible, cutthroat cretins to perform the dirty work in unforgivable mission ops that require death-defying determination. Enter U.S. Intelligence agent Amanda Waller (Oscar nominee Viola Davis). Waller's duties are to assemble the ragtag team known as the Suicide Squad--ominous (yet talented) jailbirds tapped to step in and assume superhero status (especially when the real superheroes are tied up in other crime-stopping affairs) while helping out for the greater good of our vulnerable society. In exchange for the Suicide Squad's sacrifice in turning from hell-bent heels to reluctant heralded heroes they are promised commuted prison sentences should they effectively defend and destroy the deadly foes out to promote heavy-handed havoc across the board.\r\n\r\nConveniently, bureaucratic bigwig Waller (through voiceover) introduces the Suicide Squad and describes what beneficial assets they bring to the turbulent table. Among the naughty notables include the well-known ace sniper Floyd Lawton/Deadshot as well as legendary lethal joy-boy Joker and his better (or perhaps worst half) in girlfriend Harley Quinn. The other toxic tag-a-longs along for the thrill ride of becoming rebellious rescuers include George Harkness/Boomerang (Jai Courtney), Chato Santana/El Diablo (Jay Hernandez), Waylon Jones/Killer Croc (Adewale Akinnuoye-Agbaje), Tatsu Yamashiro/Katana, Enchantress (Cara Delevingne) and Rick Flag (Joel Kinnaman).\r\n\r\nOverall, **Suicide Squad** is surprisingly depressing and goes through the proverbial motions without so much as taking advantage of its surrealistic makeup. The movie never realizes its excitable potential and drifts into yet another superhero yarn that is more patchy than pronounced. Smith's Deadshot is out in the forefront but for the most part feels restrained and not as spry and savvy as one would imagine. Leto's Joker obviously pales in comparison to the brilliant and mesmerizing psychotic take on the role that earned the late Heath Ledger his posthumous Oscar statuette. In all fairness, nobody could inhabit the Clown Prince of Crime as Ledger uncannily did with committed concentration. Still, Leto's Joker--although viciously off-balance--felt recycled and furiously empty at times. Robbie's turn as Joker's misguided main squeeze merely comes off as a bratty Barbie Doll with synthetic edginess. The other **Squad** participants settle for the back burner more or less which is a crying shame because they should have been more engaged than the tepid material allowed them to be initially.\r\n\r\nWoefully sketchy and missing the fueled opulence that one would expect emerging from this cockeyed costume caper **Suicide Squad** is a detonating dud for the missing explosive DC Comics movie brand that needs to step up the pace if they expect to make a consistent and challenging impression on the devoted fanboys at the box office looking to move beyond the sardonic fantasy-based realm of another redundant serving of a _Batman/Superman_ entry.\r\n\r\n**Suicide Squad** (2016)\r\n\r\nWarner Bros.\r\n\r\n2 hrs. 3 mins.\r\n\r\nStarring: Will Smith, Jared Leto, Margo Robbie, Viola Davis, Joel Kinnaman, Jay Hernandez, Jai Courtney, Scott Eastwood, Adewale Akinnuoye-Agbaje, Ike Barinholtz, Common, Cara Delevinge, Karen Fukuhara, Adam Beach\r\n\r\nDirected and Written by: David Ayer\r\n\r\nMPPA Rating: PG-13\r\n\r\nGenre: Superheroes Saga/Action & Adventure/Comic Book Fantasy\r\n\r\nCritic's rating: ** stars (out of 4 stars)\r\n\r\n(c) **Frank Ochieng** (2016)",
      "url": "https://www.themoviedb.org/review/57a814dc9251415cfb00309a"
    }
  ],
  "total_pages": 1,
  "total_results": 1
}
*/

@Entity(tableName = "tbl_review",
        foreignKeys = @ForeignKey(entity = MovieDetails.class,
                                    parentColumns = "ID",
                                    childColumns = "MovieID",
                                    onDelete = CASCADE))
public class MovieReview implements Parcelable {

    @ColumnInfo(index = true)
    private int MovieID;
    @PrimaryKey
    @NonNull
    private String ReviewID;
    private String Author;
    private String Content;
    private String URL;

    @Ignore
    public MovieReview() {

    }

    public MovieReview(int MovieID, String ReviewID, String Author, String Content, String URL) {
        this.MovieID = MovieID;
        this.ReviewID = ReviewID;
        this.Author = Author;
        this.Content = Content;
        this.URL = URL;
    }

    @Ignore
    private MovieReview(Parcel in) {
        MovieID = in.readInt();
        ReviewID = in.readString();
        Author = in.readString();
        Content = in.readString();
        URL = in.readString();
    }

    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel in) {
            return new MovieReview(in);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };

    public int getMovieID() { return MovieID; }

    public void setMovieID(int MovieID) { this.MovieID = MovieID; }

    public String getReviewID() {
        return ReviewID;
    }

    public void setReviewID(String reviewID) {
        this.ReviewID = reviewID;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(MovieID);
        dest.writeString(ReviewID);
        dest.writeString(Author);
        dest.writeString(Content);
        dest.writeString(URL);
    }
}
