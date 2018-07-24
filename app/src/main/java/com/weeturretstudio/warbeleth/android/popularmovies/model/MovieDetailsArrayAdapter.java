package com.weeturretstudio.warbeleth.android.popularmovies.model;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.weeturretstudio.warbeleth.android.popularmovies.R;
import com.weeturretstudio.warbeleth.android.popularmovies.utilities.NetworkUtils;

import java.util.List;

public class MovieDetailsArrayAdapter extends ArrayAdapter<MovieDetails> {

    public MovieDetailsArrayAdapter(Activity context, List<MovieDetails> movieDetails) {
        super(context, 0, movieDetails);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        MovieDetails movieDetails = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_thumbnail, parent, false);

        ImageView thumbnail = (ImageView)convertView;
        if (movieDetails != null) {
            Picasso.with(getContext()).load(
                    NetworkUtils.getPosterUri(movieDetails.getPosterPath()))
                    .into(thumbnail);
        }

        return convertView;
    }
}
