package com.weeturretstudio.warbeleth.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetails;
import com.weeturretstudio.warbeleth.android.popularmovies.model.MovieDetailsArrayAdapter;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.JSONUtils;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.NetworkUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MOVIE_API_LOADER_ID = 323272;
    private static MovieDetailsArrayAdapter movieAdapter = null;
    private static String httpResultString = "";
    private static final String placeHolderPopular = "{\"page\":1,\"total_results\":19919,\"total_pages\":996,\"results\":[{\"vote_count\":1523,\"id\":351286,\"video\":false,\"vote_average\":6.6,\"title\":\"Jurassic World: Fallen Kingdom\",\"popularity\":292.257,\"poster_path\":\"\\/c9XxwwhPHdaImA2f1WEfEsbhaFB.jpg\",\"original_language\":\"en\",\"original_title\":\"Jurassic World: Fallen Kingdom\",\"genre_ids\":[28,12,878],\"backdrop_path\":\"\\/gBmrsugfWpiXRh13Vo3j0WW55qD.jpg\",\"adult\":false,\"overview\":\"Several years after the demise of Jurassic World, a volcanic eruption threatens the remaining dinosaurs on the island of Isla Nublar. Claire Dearing, the former park manager and founder of the Dinosaur Protection Group, recruits Owen Grady to help prevent the extinction of the dinosaurs once again.\",\"release_date\":\"2018-06-06\"},{\"vote_count\":724,\"id\":260513,\"video\":false,\"vote_average\":7.7,\"title\":\"Incredibles 2\",\"popularity\":179.228,\"poster_path\":\"\\/x1txcDXkcM65gl7w20PwYSxAYah.jpg\",\"original_language\":\"en\",\"original_title\":\"Incredibles 2\",\"genre_ids\":[28,12,16,10751],\"backdrop_path\":\"\\/mabuNsGJgRuCTuGqjFkWe1xdu19.jpg\",\"adult\":false,\"overview\":\"Elastigirl springs into action to save the day, while Mr. Incredible faces his greatest challenge yet – taking care of the problems of his three children.\",\"release_date\":\"2018-06-14\"},{\"vote_count\":2624,\"id\":333339,\"video\":false,\"vote_average\":7.7,\"title\":\"Ready Player One\",\"popularity\":139.714,\"poster_path\":\"\\/pU1ULUq8D3iRxl1fdX2lZIzdHuI.jpg\",\"original_language\":\"en\",\"original_title\":\"Ready Player One\",\"genre_ids\":[12,878,28,14],\"backdrop_path\":\"\\/q7fXcrDPJcf6t3rzutaNwTzuKP1.jpg\",\"adult\":false,\"overview\":\"When the creator of a popular video game system dies, a virtual contest is created to compete for his fortune.\",\"release_date\":\"2018-03-28\"},{\"vote_count\":136,\"id\":363088,\"video\":false,\"vote_average\":7.1,\"title\":\"Ant-Man and the Wasp\",\"popularity\":134.596,\"poster_path\":\"\\/rv1AWImgx386ULjcf62VYaW8zSt.jpg\",\"original_language\":\"en\",\"original_title\":\"Ant-Man and the Wasp\",\"genre_ids\":[28,12,14,35,878],\"backdrop_path\":\"\\/ksjyNgV9RTCprnzFjkEtvP72vhJ.jpg\",\"adult\":false,\"overview\":\"As Scott Lang balances being both a superhero and a father, Hope van Dyne and Dr. Hank Pym present an urgent new mission that finds the Ant-Man fighting alongside the Wasp to uncover secrets from their past.\",\"release_date\":\"2018-07-04\"},{\"vote_count\":7095,\"id\":284053,\"video\":false,\"vote_average\":7.5,\"title\":\"Thor: Ragnarok\",\"popularity\":134.066,\"poster_path\":\"\\/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg\",\"original_language\":\"en\",\"original_title\":\"Thor: Ragnarok\",\"genre_ids\":[28,12,14,878,35],\"backdrop_path\":\"\\/kaIfm5ryEOwYg8mLbq8HkPuM1Fo.jpg\",\"adult\":false,\"overview\":\"Thor is on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the prophecy of destruction to his homeworld and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela.\",\"release_date\":\"2017-10-25\"},{\"vote_count\":5393,\"id\":299536,\"video\":false,\"vote_average\":8.4,\"title\":\"Avengers: Infinity War\",\"popularity\":130.26,\"poster_path\":\"\\/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg\",\"original_language\":\"en\",\"original_title\":\"Avengers: Infinity War\",\"genre_ids\":[12,878,14,28],\"backdrop_path\":\"\\/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg\",\"adult\":false,\"overview\":\"As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.\",\"release_date\":\"2018-04-25\"},{\"vote_count\":1099,\"id\":427641,\"video\":false,\"vote_average\":6,\"title\":\"Rampage\",\"popularity\":120.352,\"poster_path\":\"\\/3gIO6mCd4Q4PF1tuwcyI3sjFrtI.jpg\",\"original_language\":\"en\",\"original_title\":\"Rampage\",\"genre_ids\":[28,12,878,14],\"backdrop_path\":\"\\/wrqUiMXttHE4UBFMhLHlN601MZh.jpg\",\"adult\":false,\"overview\":\"Primatologist Davis Okoye shares an unshakable bond with George, the extraordinarily intelligent, silverback gorilla who has been in his care since birth.  But a rogue genetic experiment gone awry mutates this gentle ape into a raging creature of enormous size.  To make matters worse, it’s soon discovered there are other similarly altered animals.  As these newly created alpha predators tear across North America, destroying everything in their path, Okoye teams with a discredited genetic engineer to secure an antidote, fighting his way through an ever-changing battlefield, not only to halt a global catastrophe but to save the fearsome creature that was once his friend.\",\"release_date\":\"2018-04-12\"},{\"vote_count\":2202,\"id\":337167,\"video\":false,\"vote_average\":6,\"title\":\"Fifty Shades Freed\",\"popularity\":110.517,\"poster_path\":\"\\/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg\",\"original_language\":\"en\",\"original_title\":\"Fifty Shades Freed\",\"genre_ids\":[18,10749],\"backdrop_path\":\"\\/9ywA15OAiwjSTvg3cBs9B7kOCBF.jpg\",\"adult\":false,\"overview\":\"Believing they have left behind shadowy figures from their past, newlyweds Christian and Ana fully embrace an inextricable connection and shared life of luxury. But just as she steps into her role as Mrs. Grey and he relaxes into an unfamiliar stability, new threats could jeopardize their happy ending before it even begins.\",\"release_date\":\"2018-01-17\"},{\"vote_count\":60,\"id\":442249,\"video\":false,\"vote_average\":6,\"title\":\"The First Purge\",\"popularity\":95.621,\"poster_path\":\"\\/2slvblTroiT1lY9bYLK7Amigo1k.jpg\",\"original_language\":\"en\",\"original_title\":\"The First Purge\",\"genre_ids\":[28,27,878,53],\"backdrop_path\":\"\\/8FhHeKdeFIhf0pk4RkG1HqbhxRX.jpg\",\"adult\":false,\"overview\":\"To push the crime rate below one percent for the rest of the year, the New Founding Fathers of America test a sociological theory that vents aggression for one night in one isolated community. But when the violence of oppressors meets the rage of the others, the contagion will explode from the trial-city borders and spread across the nation.\",\"release_date\":\"2018-07-04\"},{\"vote_count\":11389,\"id\":135397,\"video\":false,\"vote_average\":6.5,\"title\":\"Jurassic World\",\"popularity\":89.889,\"poster_path\":\"\\/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg\",\"original_language\":\"en\",\"original_title\":\"Jurassic World\",\"genre_ids\":[28,12,878,53],\"backdrop_path\":\"\\/t5KONotASgVKq4N19RyhIthWOPG.jpg\",\"adult\":false,\"overview\":\"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.\",\"release_date\":\"2015-06-06\"},{\"vote_count\":499,\"id\":460019,\"video\":false,\"vote_average\":6,\"title\":\"Truth or Dare\",\"popularity\":82.764,\"poster_path\":\"\\/kdkNaQYZ7dhM80LsnAGKpH8ca2g.jpg\",\"original_language\":\"en\",\"original_title\":\"Truth or Dare\",\"genre_ids\":[53,27],\"backdrop_path\":\"\\/eQ5xu2pQ5Kergubto5PbbUzey28.jpg\",\"adult\":false,\"overview\":\"A harmless game of \\\"Truth or Dare\\\" among friends turns deadly when someone—or something—begins to punish those who tell a lie—or refuse the dare.\",\"release_date\":\"2018-04-12\"},{\"vote_count\":1843,\"id\":447332,\"video\":false,\"vote_average\":7.2,\"title\":\"A Quiet Place\",\"popularity\":79.592,\"poster_path\":\"\\/nAU74GmpUk7t5iklEp3bufwDq4n.jpg\",\"original_language\":\"en\",\"original_title\":\"A Quiet Place\",\"genre_ids\":[18,27,53,878],\"backdrop_path\":\"\\/roYyPiQDQKmIKUEhO912693tSja.jpg\",\"adult\":false,\"overview\":\"A family is forced to live in silence while hiding from creatures that hunt by sound.\",\"release_date\":\"2018-04-03\"},{\"vote_count\":8,\"id\":318484,\"video\":false,\"vote_average\":5.3,\"title\":\"Qayamat: City Under Threat\",\"popularity\":72.893,\"poster_path\":\"\\/e6n3Tf86MJgqtF34bLogGOpkNQf.jpg\",\"original_language\":\"hi\",\"original_title\":\"Qayamat: City Under Threat\",\"genre_ids\":[28,18,80],\"backdrop_path\":\"\\/bsiSNHnzU9brwsP1hVs1AQMt9hl.jpg\",\"adult\":false,\"overview\":\"CBI Officer Akram Sheikh (Sunil Shetty) is on the verge of nabbing three dreaded Pakistan-backed arms dealers , the brothers Ali (Arbaaz Khan) and Abbas (Sanjay Kapoor) and their common moll Laila (Isha Koppikar) . When Sheikh busts an important arms deal conducted by his three targets , Ali-Abbas join forces with Gopal (Chunky Pandey) , a corrupt scientist to get hold of a deadly virus and take the tourists at the Elphinstone Jail resort as hostage . Akram Sheikh is now compelled to get help from Rachit (Ajay Devgan) , a criminal serving jail time who was a former associate of Ali-Abbas and the only person in history to have successfully escaped the Elphinstone jail . Along with a commando team , they try to pull off a rescue attempt by breaking in the jail and rescuing the hostages .\",\"release_date\":\"2003-07-11\"},{\"vote_count\":2815,\"id\":383498,\"video\":false,\"vote_average\":7.6,\"title\":\"Deadpool 2\",\"popularity\":71.908,\"poster_path\":\"\\/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg\",\"original_language\":\"en\",\"original_title\":\"Deadpool 2\",\"genre_ids\":[28,35,878],\"backdrop_path\":\"\\/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg\",\"adult\":false,\"overview\":\"Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life.\",\"release_date\":\"2018-05-15\"},{\"vote_count\":6582,\"id\":284054,\"video\":false,\"vote_average\":7.3,\"title\":\"Black Panther\",\"popularity\":69.098,\"poster_path\":\"\\/uxzzxijgPIY7slzFvMotPv8wjKA.jpg\",\"original_language\":\"en\",\"original_title\":\"Black Panther\",\"genre_ids\":[28,12,14,878],\"backdrop_path\":\"\\/6ELJEzQJ3Y45HczvreC3dg0GV5R.jpg\",\"adult\":false,\"overview\":\"King T'Challa returns home from America to the reclusive, technologically advanced African nation of Wakanda to serve as his country's new leader. However, T'Challa soon finds that he is challenged for the throne by factions within his own country as well as without. Using powers reserved to Wakandan kings, T'Challa assumes the Black Panther mantel to join with girlfriend Nakia, the queen-mother, his princess-kid sister, members of the Dora Milaje (the Wakandan 'special forces') and an American secret agent, to prevent Wakanda from being dragged into a world war.\",\"release_date\":\"2018-02-13\"},{\"vote_count\":3439,\"id\":158015,\"video\":false,\"vote_average\":6.1,\"title\":\"The Purge\",\"popularity\":68.638,\"poster_path\":\"\\/tGGJOuLHX7UDlTz57sjfhW1qreP.jpg\",\"original_language\":\"en\",\"original_title\":\"The Purge\",\"genre_ids\":[878,27,53],\"backdrop_path\":\"\\/1sZ9Nnic1ldHhHttAMDmNxaNM04.jpg\",\"adult\":false,\"overview\":\"Given the country's overcrowded prisons, the U.S. government begins to allow 12-hour periods of time in which all illegal activity is legal. During one of these free-for-alls, a family must protect themselves from a home invasion.\",\"release_date\":\"2013-05-31\"},{\"vote_count\":2749,\"id\":238636,\"video\":false,\"vote_average\":6.6,\"title\":\"The Purge: Anarchy\",\"popularity\":66.639,\"poster_path\":\"\\/l1DRl40x2OWUoPP42v8fjKdS1Z3.jpg\",\"original_language\":\"en\",\"original_title\":\"The Purge: Anarchy\",\"genre_ids\":[27,53],\"backdrop_path\":\"\\/zWGAnbxjjwY3xFGuOeR26LGbBlG.jpg\",\"adult\":false,\"overview\":\"One night per year, the government sanctions a 12-hour period in which citizens can commit any crime they wish -- including murder -- without fear of punishment or imprisonment. Leo, a sergeant who lost his son, plans a vigilante mission of revenge during the mayhem. However, instead of a death-dealing avenger, he becomes the unexpected protector of four innocent strangers who desperately need his help if they are to survive the night.\",\"release_date\":\"2014-07-17\"},{\"vote_count\":1984,\"id\":338970,\"video\":false,\"vote_average\":6.2,\"title\":\"Tomb Raider\",\"popularity\":65.774,\"poster_path\":\"\\/ePyN2nX9t8SOl70eRW47Q29zUFO.jpg\",\"original_language\":\"en\",\"original_title\":\"Tomb Raider\",\"genre_ids\":[28,12,14,18,9648,53],\"backdrop_path\":\"\\/jLYhKjSC8LyXzWZc6VwPjMYP2wy.jpg\",\"adult\":false,\"overview\":\"Lara Croft, the fiercely independent daughter of a missing adventurer, must push herself beyond her limits when she finds herself on the island where her father disappeared.\",\"release_date\":\"2018-03-08\"},{\"vote_count\":1005,\"id\":268896,\"video\":false,\"vote_average\":5.9,\"title\":\"Pacific Rim: Uprising\",\"popularity\":55.834,\"poster_path\":\"\\/v5HlmJK9bdeHxN2QhaFP1ivjX3U.jpg\",\"original_language\":\"en\",\"original_title\":\"Pacific Rim: Uprising\",\"genre_ids\":[28,14,878,12],\"backdrop_path\":\"\\/6pT73ACl5N1VekdK3wQI8PLfz1E.jpg\",\"adult\":false,\"overview\":\"It has been ten years since The Battle of the Breach and the oceans are still, but restless. Vindicated by the victory at the Breach, the Jaeger program has evolved into the most powerful global defense force in human history. The PPDC now calls upon the best and brightest to rise up and become the next generation of heroes when the Kaiju threat returns.\",\"release_date\":\"2018-03-21\"},{\"vote_count\":8353,\"id\":102899,\"video\":false,\"vote_average\":7,\"title\":\"Ant-Man\",\"popularity\":51.955,\"poster_path\":\"\\/D6e8RJf2qUstnfkTslTXNTUAlT.jpg\",\"original_language\":\"en\",\"original_title\":\"Ant-Man\",\"genre_ids\":[878,28,12],\"backdrop_path\":\"\\/kvXLZqY0Ngl1XSw7EaMQO0C1CCj.jpg\",\"adult\":false,\"overview\":\"Armed with the astonishing ability to shrink in scale but increase in strength, master thief Scott Lang must embrace his inner-hero and help his mentor, Doctor Hank Pym, protect the secret behind his spectacular Ant-Man suit from a new generation of towering threats. Against seemingly insurmountable obstacles, Pym and Lang must plan and pull off a heist that will save the world.\",\"release_date\":\"2015-07-14\"}]}";

    private MovieDetails[] currentMovies = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportLoaderManager().initLoader(
                MOVIE_API_LOADER_ID,
                null,
                MainActivity.this);

        //TODO: Implement touch input and thumbnails for the movies returned.
        //TODO: Remove temporary immediate intent to MovieDetailsACtivity.
        //Intent launchDetailsActivity = new Intent(this, MovieDetailsActivity.class);
        //launchDetailsActivity.putExtra(MovieDetails.EXTRA_IDENTIFIER, currentMovies[5]);
        //startActivity(launchDetailsActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class MovieLoader extends AsyncTaskLoader<String> {

        private WeakReference<MainActivity> mainActivity;
        private String mMovieData;

        MovieLoader(MainActivity mainActivity) {
            super(mainActivity);
            this.mainActivity = new WeakReference<>(mainActivity);
        }

        @Override
        protected void onStartLoading() {
            if(mMovieData != null)
                deliverResult(mMovieData);
            else
                forceLoad();
        }

        @Override
        public void deliverResult(String data) {
            mMovieData = data;
            super.deliverResult(data);
        }

        @Override
        public String loadInBackground() {
            URL apiURL = NetworkUtils.getMoviesUrl(mainActivity.get());

            if(apiURL == null)
                return null;

            try {
                httpResultString = NetworkUtils.getResponseFromHttpUrl(apiURL);
                Log.v(TAG, "Popular Movies: " + httpResultString);
                return httpResultString;
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data != null) {
            Log.v(TAG, data);
            //TODO: Set future adapter and/or update UI for errors.

            //TODO: Restore supportLoaderManager once no longer in offline mode.
            //httpResultString = placeHolderPopular;
            JSONObject parsedObject = JSONUtils.parseStringToJSON(data);
            currentMovies = JSONUtils.parseMovies(parsedObject);
            Log.v(TAG, "Current Movies:");
            for(int i = 0; i < currentMovies.length; i++)
                Log.v(TAG, "Movie["+i+"]: " + currentMovies[i].getMovieName() + "\n");

            movieAdapter = new MovieDetailsArrayAdapter(
                    MainActivity.this,
                    Arrays.asList(currentMovies));

            GridView gridView = (GridView)MainActivity.this.findViewById(R.id.main_gridview);
            gridView.setAdapter(movieAdapter);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        //TODO: What should be done on reset?
    }
}
