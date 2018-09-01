package com.weeturretstudio.warbeleth.android.popularmovies.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weeturretstudio.warbeleth.android.popularmovies.R;

import java.util.ArrayList;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ReviewViewHolder> {
    private static final String TAG = MovieReviewAdapter.class.getSimpleName();

    private ArrayList<MovieReview> movieReviews;

    public MovieReviewAdapter(ArrayList<MovieReview> reviews) {
        this.setReviews(reviews);
    }

    public void setReviews(ArrayList<MovieReview> reviews) {
        movieReviews = reviews;
        this.notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView mAuthor;
        TextView mContent;
        TextView mURL;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            mAuthor = itemView.findViewById(R.id.review_view_name);
            mContent = itemView.findViewById(R.id.review_view_site);
            mURL = itemView.findViewById(R.id.review_view_key);
        }

        public void SetupViewHolder(String author, String content, String url) {
            mAuthor.setText(author);
            mContent.setText(content);
            mURL.setText(url);
        }
    }

    @NonNull
    @Override
    public MovieReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForVideo = R.layout.movie_review_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachImmediately = false;

        View view = inflater.inflate(layoutIdForVideo, parent,
                shouldAttachImmediately);
        MovieReviewAdapter.ReviewViewHolder viewHolder = new MovieReviewAdapter.ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewAdapter.ReviewViewHolder holder, int position) {
        holder.SetupViewHolder(movieReviews.get(position).getAuthor(),
                movieReviews.get(position).getContent(),
                movieReviews.get(position).getURL());
    }

    @Override
    public int getItemCount() {
        if(null == movieReviews)
            return 0;

        return movieReviews.size();
    }
}
